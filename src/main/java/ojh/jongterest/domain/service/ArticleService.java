package ojh.jongterest.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.repository.article.ArticleRepository;
import ojh.jongterest.domain.entity.Comment;
import ojh.jongterest.domain.repository.comment.CommentRepository;
import ojh.jongterest.common.imageFile.ImageFile;
import ojh.jongterest.domain.entity.Article;
import ojh.jongterest.domain.entity.LikeRecord;
import ojh.jongterest.domain.repository.likeRecord.LikeRecordRepository;
import ojh.jongterest.domain.entity.Project;
import ojh.jongterest.domain.repository.project.ProjectRepository;
import ojh.jongterest.domain.repository.subscription.SubscriptionRepository;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.domain.repository.user.UserRepository;
import ojh.jongterest.file.FileStore;
import ojh.jongterest.web.controller.article.ArticleForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

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
//        Article newArticle = new Article(user, articleForm.getTitle(), articleForm.getContent(), articleImage, project);
        Article newArticle = Article.builder()
                .user(user)
                .title(articleForm.getTitle())
                .content(articleForm.getContent())
                .articleImage(articleImage)
                .project(project)
                .build();

//        newArticle.setUser(user);

        //양방향
        project.addArticle(newArticle);

        //TODO
        articleRepository.save(newArticle);

        return newArticle;
    }

    public List<Article> getArticleList(int page) {

        int offset = (page -1) * 10;
        int limit = 10;

        return articleRepository.findAllOrderByUpdateAtDesc(offset,limit);

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


    public List<Article> findArticlesWithUserProjectArticleContainingUserOrderByUpdatedAt(User loginUser, int page) {
        int offset = (page -1) * 10;
        int limit = 10;
        List<Article> articles = articleRepository.findAllWithUserProjectArticleContainingUserOrderByUpdatedAt(loginUser, offset, limit);

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
