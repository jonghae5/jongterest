package ojh.jongterest.domain.subscription;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.article.ArticleRepository;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.project.ProjectRepository;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ArticleRepository articleRepository;

    public Boolean isSubscription(Long userId,Long projectId) {
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);
        log.info("subscriptions={}",subscriptions);
        for (Subscription subscription : subscriptions) {
            if (subscription.getProject().getProjectId() == projectId) {
                return true;
            }
        }
        return false;
    }

    public void hello() {
        System.out.println("hello world");
    }

    public List<Article> findAllBySubscription(User loginUser) {
        List<Long> projectIds = subscriptionRepository.findProjectIdsByUserId(loginUser.getUserId());

        ArrayList<Article> result = new ArrayList<>();
        for (Long projectId : projectIds) {
            result.addAll(articleRepository.findAllByProjectId(projectId));
        }

        return result;

    }

    public void saveSubscription(Long userId, Long projectId) {
        log.info("Service 실행");
        User user = userRepository.findById(userId);
        Project project = projectRepository.findById(projectId);
        Subscription subscription = new Subscription(user, project);
        log.info("Service2 실행");

        subscriptionRepository.save(subscription);
    }

    public void deleteSubscription(Long userId, Long projectId) {
        log.info("delete 실행");
        subscriptionRepository.delete(userId, projectId);

    }
}
