package ojh.jongterest.domain.article.repository;

import ojh.jongterest.domain.article.Article;

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
