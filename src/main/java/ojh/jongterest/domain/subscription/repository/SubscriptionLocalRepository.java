package ojh.jongterest.domain.subscription.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.subscription.Subscription;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//@Repository
@RequiredArgsConstructor
@Slf4j
public class SubscriptionLocalRepository implements SubscriptionRepositoryOld {
    private static final ConcurrentHashMap<Long, Subscription> subscriptionRepository = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, Long> subscriptionProjectRepository = new ConcurrentHashMap<>();
    private static final MultiValueMap<Long, Long> userSubscriptionRepository = new LinkedMultiValueMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);

    @Override
    public void save(Subscription subscription) {
        log.info("Repository 실행");
        subscription.setSubscriptionId(sequence.addAndGet(1));

        Long userId = subscription.getUser().getUserId();
        Long projectId = subscription.getProject().getProjectId();
        Long subscriptionId = subscription.getSubscriptionId();

        subscriptionRepository.put(subscriptionId,subscription);
        subscriptionProjectRepository.put(subscriptionId,projectId);
        userSubscriptionRepository.add(userId, subscriptionId);

    }
    @Override
    public Optional<Subscription> findOne(Long subscriptionId) {

            return Optional.ofNullable(subscriptionRepository.get(subscriptionId));

    }
    @Override
    public List<Subscription> findByUserId(Long userId) {
        Optional<List<Long>> subscriptionIds =  Optional.ofNullable(userSubscriptionRepository.get(userId));
        if (subscriptionIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Subscription> result = new ArrayList<>();
        for (Long subscriptionId : subscriptionIds.get()) {
            if (findOne(subscriptionId).isPresent()) {
            result.add(findOne(subscriptionId).get());
            }
        }
        return result;
    }

    public List<Long> findProjectIdsByUserId(Long userId) {
        Optional<List<Long>> subscriptionIds = Optional.ofNullable(userSubscriptionRepository.get(userId));
        if (subscriptionIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> result = new ArrayList<>();
        for (Long subscriptionId : subscriptionIds.get()) {
            Long projectId = subscriptionProjectRepository.get(subscriptionId);
            result.add(projectId);
        }

        return result;
    }

    public void delete(Long userId, Long projectId) {
        log.info("DELETE 실행");
        List<Long> subscriptionIds = userSubscriptionRepository.get(userId);

        for (Long subscriptionId : subscriptionIds) {
            Long findProjectId = subscriptionProjectRepository.get(subscriptionId);
            if (findProjectId == projectId) {
                subscriptionIds.remove(subscriptionId);
                userSubscriptionRepository.replace(userId, subscriptionIds);
                subscriptionProjectRepository.remove(subscriptionId, projectId);
                subscriptionRepository.remove(subscriptionId);
                break;
            }
        }
    }

}
