package ojh.jongterest.domain.repository.project;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.entity.Article;
import ojh.jongterest.domain.entity.Project;
import ojh.jongterest.domain.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProjectJpaRepository implements ProjectRepository{
    private final EntityManager em;
    @Override
    public void save(Project project) {
        if (project.getProjectId() == null) {
            em.persist(project);
        } else {
            em.merge(project);
        }
    }

    @Override
    public Optional<Project> findOne(Long projectId) {
        return Optional.ofNullable(em.find(Project.class, projectId));
    }

    @Override
    public List<Project> findAll() {
        return em.createQuery("select p from Project p", Project.class)
                .getResultList();
    }

    @Override
    public void deleteById(Long projectId) {
        if (findOne(projectId).isPresent()) {
            em.remove(findOne(projectId).get());
        }
    }


    @Override
    public List<Project> findByUserId(Long userId) {
        return em.createQuery("select p from Project p " +
                        "where p.user.userId =: id", Project.class)
                .setParameter("id", userId)
                .getResultList();
    }


    @Override
    public List<Project> findAllWithSubscriptionsContainingUser(User user) {
        return em.createQuery("select p from Project p " +
                        "join fetch p.subscriptions s " +
                        "join fetch s.user u " +
                        "where u.userId =: id", Project.class)
                .setParameter("id", user.getUserId())
                .getResultList();
    }

    @Override
    public List<Project> findAllOrderByUpdateAtDesc(int offset, int limit) {
        return em.createQuery(
                        "select p from Project p order by p.updatedAt desc", Project.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
