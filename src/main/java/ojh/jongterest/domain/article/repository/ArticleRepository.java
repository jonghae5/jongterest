package ojh.jongterest.domain.article.repository;

import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    void save(Article article);
    Optional<Article> findOne(Long articleId);
    List<Article> findByUserId(Long userId);
    List<Article> findAll();
    List<Article> findByProjectId(Long projectId);
    void update(Article article);
    void delete(Article article);

    List<Article> findAllWithUserProjectArticleContainingUser(User user);


}
