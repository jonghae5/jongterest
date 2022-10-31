package ojh.jongterest.domain.repository.subscription;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.entity.Subscription;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SubscriptionJpaRepository implements SubscriptionRepository {

    private final EntityManager em;
    @Override
    public Optional<Subscription> findOne(Long subscriptionId) {
        return Optional.ofNullable(em.find(Subscription.class, subscriptionId));
    }

    @Override
    public List<Subscription> findByUserId(Long userId) {
        return em.createQuery("select sc from Subscription sc " +
                        "where sc.user.userId =: id", Subscription.class)
                .setParameter("id", userId)
                .getResultList();
    }

    @Override
    public void save(Subscription subscription) {
        if (subscription.getSubscriptionId() == null) {
            em.persist(subscription);
        } else {
            em.merge(subscription);
        }
    }

    @Override
    public void deleteByUserIdAndProjectId(Long userId, Long projectId) {
        em.createQuery("DELETE FROM Subscription sc " +
                        "join fetch sc.user u " +
                        "join fetch sc.project p" +
                        "WHERE u.userId =:userId and p.projectId =:projectId")
                .setParameter("userId", userId)
                .setParameter("projectId", projectId).executeUpdate();
    }

    @Override
    public Optional<Subscription> findByUserIdAndProjectId(Long userId, Long projectId) {
        List<Subscription> subscriptions =
                em.createQuery("SELECT sc FROM Subscription sc " +
                                "join fetch sc.user u " +
                                "join fetch sc.project p" +
                                "WHERE u.userId =:userId and p.projectId =:projectId")
                .setParameter("userId", userId)
                .setParameter("projectId", projectId).getResultList();
        return subscriptions.stream().findAny();
    }

    @Override
    public List<Subscription> findByProjectId(Long projectId) {
        return em.createQuery("select sc from Subscription sc " +
                        "join fetch sc.project p" +
                        "where p.projectId =: id", Subscription.class)
                .setParameter("id",projectId)
                .getResultList();
    }

    @Override
    public void delete(Subscription subscription) {
        em.remove(subscription);
    }


}
