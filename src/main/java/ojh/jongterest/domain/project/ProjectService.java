package ojh.jongterest.domain.project;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.article.ArticleRepository;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectService {

    private final ArticleRepository articleRepository;
    private final ProjectRepository projectRepository;

    public List<Project> getProjectList() {
        List<Project> all = projectRepository.findAll();

        // 10개만 가져오기
        if (all.size() > 10) {
            return all.subList(0, 10);
        }
        return all;

    }

    public Project createProject(User loginUser, String title, String description, ImageFile projectImage) {

        Project project = new Project();
        Project createProject = project.create(title, description, LocalDateTime.now(), projectImage, loginUser);
        Project result = projectRepository.save(project);

        return result;
    }

    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId);
        List<Long> articleIds = projectRepository.findArticleIdsByProjectId(projectId);
        for (Long articleId : articleIds) {
            articleRepository.delete(articleRepository.findById(articleId));
        }
        projectRepository.delete(projectId);

    }
}
