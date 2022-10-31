package ojh.jongterest.domain.repository.article;

import ojh.jongterest.domain.entity.Article;
import ojh.jongterest.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);
    Optional<Article> findOne(Long articleId);
    List<Article> findByUserId(Long userId);
    List<Article> findAll();
    List<Article> findByProjectId(Long projectId);
    void update(Article article);
    void delete(Article article);
    List<Article> findAllWithUserProjectArticleContainingUserOrderByUpdatedAt(User user, int offset, int limit);
    List<Article> findAllOrderByUpdateAtDesc(int offset, int limit);

}
