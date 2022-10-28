package ojh.jongterest.domain.subscription;

import ojh.jongterest.domain.project.Project;

import java.util.List;

public interface SubscriptionRepository {
    Subscription findById(Long subscriptionId);
    List<Subscription> findByUserId(Long userId);
    Subscription save(Subscription subscription);
    void delete(Long userId, Long projectId);
    List<Long> findProjectIdsByUserId(Long userId);
}
