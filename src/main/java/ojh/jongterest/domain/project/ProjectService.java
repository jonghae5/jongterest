package ojh.jongterest.domain.project;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.article.ArticleService;
import ojh.jongterest.domain.article.repository.ArticleRepository;
import ojh.jongterest.domain.comment.Comment;
import ojh.jongterest.domain.comment.CommentService;
import ojh.jongterest.domain.comment.repository.CommentRepository;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.likeRecord.LikeRecord;
import ojh.jongterest.domain.likeRecord.repository.LikeRecordRepository;
import ojh.jongterest.domain.project.repository.ProjectRepository;
import ojh.jongterest.domain.subscription.Subscription;
import ojh.jongterest.domain.subscription.repository.SubscriptionRepository;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.file.FileStore;
import ojh.jongterest.web.controller.project.ProjectForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final ProjectRepository projectRepository;
    private final LikeRecordRepository likeRecordRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final FileStore fileStore;

    public List<Project> getProjectList() {
        List<Project> all = projectRepository.findAll();

        // 10개만 가져오기
        if (all.size() > 10) {
            return all.subList(0, 10);
        }
        return all;

    }

    @Transactional
    public void createProject(User user, ProjectForm projectForm) throws IOException {
        ImageFile projectImage = fileStore.storeFile(projectForm.getProjectImage());
        Project project = new Project();
        project.create(projectForm.getTitle(), projectForm.getDescription(), LocalDateTime.now(), projectImage, user);


        projectRepository.save(project);

    }

    @Transactional
    public void deleteProjectById(Long projectId) {
        // Project 삭제
        Optional<Project> project = projectRepository.findOne(projectId);
        // 나 자신이 아닌 다른 사람이 한 것들을 다 삭제한다.
        if (project.isPresent()) {

            log.info("project 실행");
            List<Article> articles = articleRepository.findByProjectId(project.get().getProjectId());
            if (articles.size() > 0) {
                for (Article article : articles) {
                    log.info("article 실행");
                    List<Comment> comments = article.getComments();
                    if (comments.size() > 0) {
//                            article.deleteComments(comments);
                        for (Comment comment : comments) {
                            commentRepository.delete(comment);
                        }
                    }
                    List<LikeRecord> likeRecords = article.getLikeRecords();
                    if (likeRecords.size() > 0) {
//                            article.deleteLikeRecords(likeRecords);
                        for (LikeRecord likeRecord : likeRecords) {
                            likeRecordRepository.deleteById(likeRecord.getLikeRecordId());
                        }
                    }
                    articleRepository.delete(article);
                    project.get().deleteArticle(article);
                }
            }
            List<Subscription> subscriptions = project.get().getSubscriptions();
            if (subscriptions.size() > 0) {
                project.get().deleteSubscriptions(subscriptions);
                for (Subscription subscription : subscriptions) {
                    subscriptionRepository.delete(subscription);
                }
            }
            projectRepository.deleteById(projectId);
        }
    }
}

