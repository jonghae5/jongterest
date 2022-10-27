package ojh.jongterest.domain.article;

import ojh.jongterest.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Long userId, Article article);
    Article findById(Long articleId);
    List<Article> findByUser(Long userId);

    List<Article> findAll();
    Article update(Article article);
    void delete(Long userId, Long articleId);
    void clearStore();
}
