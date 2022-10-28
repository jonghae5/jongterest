package ojh.jongterest.domain.article;

import java.util.List;

public interface ArticleRepository {

    Article save(Long userId, Article article);
    Article findById(Long articleId);
    List<Article> findByUser(Long userId);

    List<Article> findAll();
    Article update(Article article);
    void delete(Article article);
    void clearStore();

    List<Article> findAllByProjectId(Long projectId);
}
