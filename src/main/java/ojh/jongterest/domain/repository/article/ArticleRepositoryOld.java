package ojh.jongterest.domain.repository.article;

import ojh.jongterest.domain.entity.Article;

import java.util.List;

public interface ArticleRepositoryOld {

    Article save(Long userId, Article article);
    Article findOne(Long articleId);
    List<Article> findByUserId(Long userId);

    List<Article> findAll();
    Article update(Article article);
    void delete(Article article);
    void clearStore();

    List<Article> findByProjectId(Long projectId);
}
