package ojh.jongterest.domain.project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Project save(Project project);
    Project findById(Long projectId);
    List<Project> findAll();
    void delete(Long projectId);
    List<Long> findArticleIdsByProjectId(Long projectId);

    List<Project> findByUserId(Long userId);
}
