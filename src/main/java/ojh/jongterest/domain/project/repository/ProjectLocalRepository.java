package ojh.jongterest.domain.project.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.user.User;
import org.springframework.stereotype.Repository;
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
public class ProjectLocalRepository implements ProjectRepository{
    private static final MultiValueMap<Long, Long> userProjectRepository = new LinkedMultiValueMap<>();
    private static final MultiValueMap<Long, Long> projectArticleRepository = new LinkedMultiValueMap<>();
    private static final ConcurrentHashMap<Long, Project> projectRepository = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);
    @Override
    public Optional<Project> findOne(Long projectId) {
        return Optional.ofNullable(projectRepository.get(projectId));
    }

    @Override
    public List<Project> findAll() {
        return new ArrayList<>(projectRepository.values());
    }

    @Override
    public void save(Project project) {
        Long userId = project.getUser().getUserId();
        project.setProjectId(sequence.addAndGet(1));
        projectRepository.put(project.getProjectId(), project);
        log.info("userId DEBUG={}", userId);
        userProjectRepository.add(userId,project.getProjectId());
//        projectArticleRepository.add(project.getProjectId(), articleId);
    }

    @Override
    public void deleteById(Long projectId) {
        if (findOne(projectId).isPresent()) {
            Project project = findOne(projectId).get();
            Long userId = project.getUser().getUserId();
            List<Long> projectIds = userProjectRepository.get(userId);
            projectIds.remove(projectId);
            userProjectRepository.replace(userId,projectIds);
            projectRepository.remove(projectId);
            projectArticleRepository.remove(projectId);
        }
    }

//    @Override
//    public List<Long> findArticleIdsByProjectId(Long projectId) {
//        return projectArticleRepository.get(projectId);
//    }

    @Override
    public List<Project> findByUserId(Long userId) {
        Optional<List<Long>> projectIds = Optional.ofNullable(userProjectRepository.get(userId));
        if (projectIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Project> result = new ArrayList<>();
        for (Long projectId : projectIds.get()) {
            if (findOne(projectId).isPresent()) {
                result.add(findOne(projectId).get());
            }
        }
        return result;
    }

    public List<Project> findAllWithSubscriptionsContainingUser(User user) {
        return new ArrayList<>();
    }
}
