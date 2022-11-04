package ojh.jongterest.domain.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.repository.article.ArticleRepository;
import ojh.jongterest.domain.entity.Project;
import ojh.jongterest.domain.repository.project.ProjectRepository;
import ojh.jongterest.domain.entity.Subscription;
import ojh.jongterest.domain.repository.subscription.SubscriptionRepository;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.domain.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Subscription subscription = Subscription.builder()
                .project(project)
                .user(user)
                .build();
        project.addSubscription(subscription);
        subscriptionRepository.save(subscription);
    }
    @Transactional
    public void deleteSubscription(Long userId, Long projectId) {
        Optional<Subscription> subscription = subscriptionRepository.findByUserIdAndProjectId(userId, projectId);
        if (subscription.isPresent()) {
            subscription.get().getProject().getSubscriptions().remove(subscription);
            subscriptionRepository.delete(subscription.get());
        }
//        subscriptionRepository.deleteByUserIdAndProjectId(userId, projectId);
    }
}
