package ojh.jongterest.web;

import lombok.RequiredArgsConstructor;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.article.ArticleRepository;
import ojh.jongterest.domain.comment.Comment;
import ojh.jongterest.domain.comment.CommentRepository;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.UserRepository;
import ojh.jongterest.domain.user.profile.UserProfile;
import ojh.jongterest.web.controller.comment.CommentForm;
import ojh.jongterest.web.controller.user.Gender;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        User user = new User("123","123", Gender.MALE);
        user.setJoinedDate(LocalDateTime.now());
        User user2 = new User("dhwhdgo2368","123", Gender.FEMALE);
        user2.setJoinedDate(LocalDateTime.now());

        ImageFile profileImage = new ImageFile("default.jpeg", "default.jpeg");
        UserProfile userProfile = new UserProfile(profileImage, "John", "테스트메세지");
        user.createProfile(userProfile);


        ImageFile profileImage2 = new ImageFile("default.jpeg", "default.jpeg");
        UserProfile userProfile2 = new UserProfile(profileImage2, "John2", "테스트메세지2");
        user2.createProfile(userProfile2);

        Article article = new Article(user, "Test Article", "컨텐츠 Test", profileImage);

        CommentForm commentForm = new CommentForm();
        commentForm.setContent("Test Content");

        Comment comment = new Comment(commentForm.getContent(), LocalDateTime.now(), LocalDateTime.now());
        comment.setUser(user);
        List<Comment> commentsList = new ArrayList<>();
        commentsList.add(comment);
        article.setComments(commentsList);


        userRepository.save(user);
        userRepository.save(user2);

        articleRepository.save(user.getUserId(), article);
        commentRepository.save(user.getUserId(),article.getArticleId(), comment);


    }

}