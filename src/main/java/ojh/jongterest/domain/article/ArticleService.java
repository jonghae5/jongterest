package ojh.jongterest.domain.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.project.ProjectRepository;
import ojh.jongterest.domain.subscription.Subscription;
import ojh.jongterest.domain.subscription.SubscriptionRepository;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final SubscriptionRepository subscriptionRepository;

    public Article saveArticle(Long userId, Article article) {
        User findUser = userRepository.findById(userId);
        article.setUser(findUser);
        articleRepository.save(userId, article);

        Project findProject = projectRepository.findById(article.getProject().getProjectId());
        findProject.getArticles().add(article);

        return article;
    }

    public List<Article> getArticleList() {
        List<Article> all = articleRepository.findAll();

        // 10개만 가져오기
        if (all.size() > 10) {
            return all.subList(0,10);
        }
        return all;

    }

    public void deleteArticle(Article article) {
        log.info("DEBUG 오류찾기={}",projectRepository.findById(article.getProject().getProjectId()));
        Project findProject = projectRepository.findById(article.getProject().getProjectId());
        findProject.getArticles().remove(article);
        articleRepository.delete(article);
    }

    public void updateArticle(Article findArticle) {
        articleRepository.update(findArticle);
    }

}
