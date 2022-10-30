package ojh.jongterest.domain.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.article.repository.ArticleRepository;
import ojh.jongterest.domain.comment.Comment;
import ojh.jongterest.domain.comment.repository.CommentRepository;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.likeRecord.LikeRecord;
import ojh.jongterest.domain.likeRecord.repository.LikeRecordRepository;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.project.repository.ProjectRepository;
import ojh.jongterest.domain.subscription.repository.SubscriptionRepository;
import ojh.jongterest.domain.subscription.repository.SubscriptionRepositoryOld;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.repository.UserRepository;
import ojh.jongterest.file.FileStore;
import ojh.jongterest.web.controller.article.ArticleForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final CommentRepository commentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final LikeRecordRepository likeRecordRepository;
    private final FileStore fileStore;

    @Transactional
    public Article saveArticle(User user, ArticleForm articleForm) throws IOException {

        ImageFile articleImage = fileStore.storeFile(articleForm.getArticleImage());
        Project project = projectRepository.findOne(articleForm.getProjectId()).get();
        Article newArticle = new Article(user, articleForm.getTitle(), articleForm.getContent(), articleImage, project);

        newArticle.setUser(user);
        //양방향
        project.addArticle(newArticle);

        //TODO
        articleRepository.save(newArticle);

        return newArticle;
    }

    public List<Article> getArticleList() {

        List<Article> all = articleRepository.findAll();

        // 10개만 가져오기
        if (all.size() > 10) {
            return all.subList(0, 10);
        }
        return all;

    }

    @Transactional
    public void deleteArticle(Article article) {

        //양방향 삭제
        Project project = article.getProject();

        // 고아 객체를 만든다.
        project.deleteArticle(article);

//        Optional<Article> article = articleRepository.findOne(deleteArticle.getArticleId());

        log.info("article 실행");
        List<Comment> comments = article.getComments();
        if (comments.size() > 0) {
//
            for (Comment comment : comments) {
                commentRepository.delete(comment);
            }
        }
        List<LikeRecord> likeRecords = article.getLikeRecords();
        if (likeRecords.size() > 0) {

            for (LikeRecord likeRecord : likeRecords) {
                likeRecordRepository.deleteById(likeRecord.getLikeRecordId());
            }
        }
        articleRepository.delete(article);
    }


//        articleRepository.delete(article);


    public List<Article> findArticlesWithUserProjectArticleContainingUser(User loginUser) {
        List<Article> articles = articleRepository.findAllWithUserProjectArticleContainingUser(loginUser);

        return articles;
    }

    @Transactional
    public void updateArticle(Article findArticle, ArticleForm articleForm) throws IOException {

        if (articleForm.getArticleImage().getOriginalFilename().isEmpty()) {
            findArticle.update(articleForm.getTitle(), articleForm.getContent(), findArticle.getArticleImage());
        } else {
            ImageFile articleImage = fileStore.storeFile(articleForm.getArticleImage());
            findArticle.update(articleForm.getTitle(), articleForm.getContent(), articleImage);
        }
        // 안해줘도 됨
        articleRepository.update(findArticle);
    }

}
