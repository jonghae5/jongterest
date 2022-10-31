package ojh.jongterest.domain.repository.subscription;

import ojh.jongterest.domain.entity.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepositoryOld {
    Optional<Subscription> findOne(Long subscriptionId);
    List<Subscription> findByUserId(Long userId);
    void save(Subscription subscription);
    void delete(Long userId, Long projectId);

//    List<Long> findProjectIdsByUserId(Long userId);
}
