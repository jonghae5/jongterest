package ojh.jongterest.domain.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ArticleLocalRepository implements ArticleRepository{

    private final UserRepository userRepository;
    // user, articles
    private static final MultiValueMap<Long, Long> userArticleRepository = new LinkedMultiValueMap<>();
    private static final ConcurrentHashMap<Long, Article> articleRepository = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong(0);

    @Override
    public Article save(Long userId,Article article) {

        article.setArticleId(sequence.addAndGet(1));

        articleRepository.put(sequence.get(),article);
        userArticleRepository.add(userId, article.getArticleId());
        return article;
    }

    @Override
    public Article findById(Long articleId) {
        return articleRepository.get(articleId);
    }

    @Override
    public List<Article> findByUser(Long userId) {
        List<Long> articlesId = userArticleRepository.get(userId);
        List<Article> articles = new ArrayList<>();

        for (Long articleId : articlesId) {
            Article findArticle = findById(articleId);
            articles.add(findArticle);
        }

        return articles;
    }

    @Override
    public List<Article> findAll() {
        List<Long> articlesId = userArticleRepository.values().stream().flatMap(m -> m.stream())
                .collect(Collectors.toList());

        ArrayList<Article> articles = new ArrayList<>();
        for (Long articleId : articlesId) {
            Article findArticle = findById(articleId);
            articles.add(findArticle);
        }
        if (articles.isEmpty()) {
            return Collections.emptyList();
        }
        return articles;
    }

    @Override
    public Article update(Article article) {
        articleRepository.replace(article.getArticleId(), article);
        return article;
    }

    @Override
    public void delete(Long userId, Long articleId) {
        Article findArticle = findById(articleId);

        List<Long> findArticleIds = userArticleRepository.get(userId);
        findArticleIds.remove(articleId);
        userArticleRepository.replace(userId, findArticleIds);
        articleRepository.remove(articleId);
    }
    @Override
    public void clearStore() {
        userArticleRepository.clear();
        articleRepository.clear();
    }
}
