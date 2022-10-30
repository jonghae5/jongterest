package ojh.jongterest.domain.subscription.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.subscription.Subscription;
import ojh.jongterest.domain.user.User;
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
        return em.createQuery("select sc from Subscription sc where sc.user.userId =: id", Subscription.class)
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
        em.createQuery("DELETE FROM Subscription sc WHERE sc.user.userId =:userId and sc.project.projectId =:projectId")
                .setParameter("userId", userId)
                .setParameter("projectId", projectId).executeUpdate();
    }

    @Override
    public Optional<Subscription> findByUserIdAndProjectId(Long userId, Long projectId) {
        List<Subscription> subscriptions = em.createQuery("select sc from Subscription sc WHERE sc.user.userId =:userId and sc.project.projectId =:projectId", Subscription.class)
                .setParameter("userId", userId)
                .setParameter("projectId", projectId).getResultList();
        return subscriptions.stream().findAny();
    }

    @Override
    public List<Subscription> findByProjectId(Long projectId) {
        return em.createQuery("select sc from Subscription sc where sc.project.projectId =: id", Subscription.class)
                .setParameter("id",projectId)
                .getResultList();
    }

    @Override
    public void delete(Subscription subscription) {
        em.remove(subscription);
    }


}
