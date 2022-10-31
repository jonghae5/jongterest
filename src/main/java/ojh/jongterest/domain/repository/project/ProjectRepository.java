package ojh.jongterest.domain.repository.project;

import ojh.jongterest.domain.entity.Project;
import ojh.jongterest.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    void save(Project project);
    Optional<Project> findOne(Long projectId);
    List<Project> findAll();
    void deleteById(Long projectId);

    List<Project> findByUserId(Long userId);
    List<Project> findAllWithSubscriptionsContainingUser(User user);
}
