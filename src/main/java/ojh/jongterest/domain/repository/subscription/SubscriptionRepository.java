package ojh.jongterest.domain.repository.subscription;

import ojh.jongterest.domain.entity.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    Optional<Subscription> findOne(Long subscriptionId);
    List<Subscription> findByUserId(Long userId);
    void save(Subscription subscription);
    void deleteByUserIdAndProjectId(Long userId, Long projectId);

    Optional<Subscription> findByUserIdAndProjectId(Long userId,  Long projectId);


    List<Subscription> findByProjectId(Long projectId);

    void delete(Subscription subscription);
}
