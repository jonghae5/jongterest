package ojh.jongterest.domain.article.repository;

import lombok.RequiredArgsConstructor;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class ArticleJpaRepository implements ArticleRepository {

    private final EntityManager em;

    @Override
    public void save(Article article) {
        if (article.getArticleId() == null) {
            em.persist(article);
        } else {
            em.merge(article);
        }
    }

    @Override
    public Optional<Article> findOne(Long articleId) {
        return Optional.ofNullable(em.find(Article.class, articleId));
    }

    @Override
    public List<Article> findByUserId(Long userId) {
        return em.createQuery("select a from Article a where a.user.userId =:id", Article.class)
                .setParameter("id", userId)
                .getResultList();
    }

    @Override
    public List<Article> findAll() {
        return em.createQuery("select a from Article a", Article.class).getResultList();
    }

    @Override
    public void update(Article article) {
        if (article.getArticleId() == null) {
            em.persist(article);
        } else {
            em.merge(article);
        }
    }

    @Override
    public void delete(Article article) {
        em.remove(article);
//        em.createQuery("DELETE FROM Article a WHERE a.articleId = :id")
//                .setParameter("id", article.getArticleId()).executeUpdate();
    }


    @Override
    public List<Article> findByProjectId(Long projectId) {
        return em.createQuery("select a from Article a where a.project.projectId =: id", Article.class)
                .setParameter("id", projectId)
                .getResultList();
    }

    @Override
    public List<Article> findAllWithUserProjectArticleContainingUser(User user) {

        return em.createQuery("select a from Article a " +
                        "join fetch a.user u " +
                        "join fetch a.project p " +
                        "join fetch p.subscriptions s " +
                        "where s.user.userId =: id", Article.class)
                .setParameter("id", user.getUserId())
                .getResultList();
    }
}

