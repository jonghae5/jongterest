package ojh.jongterest.domain.subscription;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.article.repository.ArticleRepository;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.project.repository.ProjectRepository;
import ojh.jongterest.domain.subscription.repository.SubscriptionRepository;
import ojh.jongterest.domain.subscription.repository.SubscriptionRepositoryOld;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ArticleRepository articleRepository;


    public Boolean isSubscription(Long userId,Long projectId) {
        Optional<Subscription> subscription = subscriptionRepository.findByUserIdAndProjectId(userId, projectId);
        if (subscription.isPresent()) {
            return true;
        }
        return false;
    }


    @Transactional
    public void saveSubscription(Long userId, Long projectId) {
        User user = userRepository.findOne(userId).get();
        Project project = projectRepository.findOne(projectId).get();
        Subscription subscription = new Subscription(user, project);
        log.info("Service 실행");
        project.addSubscription(subscription);
        subscriptionRepository.save(subscription);
    }
    @Transactional
    public void deleteSubscription(Long userId, Long projectId) {
        log.info("delete 실행");
        Optional<Subscription> subscription = subscriptionRepository.findByUserIdAndProjectId(userId, projectId);
        if (subscription.isPresent()) {
            subscription.get().getProject().getSubscriptions().remove(subscription);
            subscriptionRepository.delete(subscription.get());
        }
//        subscriptionRepository.deleteByUserIdAndProjectId(userId, projectId);
    }
}
