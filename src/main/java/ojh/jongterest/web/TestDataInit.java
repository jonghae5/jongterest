package ojh.jongterest.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.article.repository.ArticleRepository;
import ojh.jongterest.domain.article.ArticleService;
import ojh.jongterest.domain.comment.Comment;
import ojh.jongterest.domain.comment.repository.CommentRepository;
import ojh.jongterest.domain.comment.repository.CommentRepositoryOld;
import ojh.jongterest.domain.comment.CommentService;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.project.repository.ProjectRepository;
import ojh.jongterest.domain.project.ProjectService;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.repository.UserRepository;
import ojh.jongterest.domain.user.UserService;
import ojh.jongterest.domain.user.profile.UserProfile;
import ojh.jongterest.web.controller.comment.CommentForm;
import ojh.jongterest.web.controller.user.Gender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataInit {

    private final InitService initService;
    @PostConstruct
    public void init() {
        initService.initData();
    }

    /**
     * 테스트용 데이터 서비스
     */
    @RequiredArgsConstructor
    @Component
    @Transactional
    static class InitService {
        private final EntityManager em;

        public void initData() {
            User user = createUser("123", "123", Gender.MALE);
            User user2 = createUser("1234", "123", Gender.FEMALE);


            ImageFile profileImage = new ImageFile("default.jpeg", "default.jpeg");

            createProfile(user, "John", "테스트메세지", profileImage);
            createProfile(user2, "John2", "테스트메세지2", profileImage);
            em.persist(user);
            em.persist(user2);
            log.info("TEST 초기 유저 데이터 완료");

            Project project = new Project();
            ImageFile projectImage = new ImageFile("default.jpeg", "default.jpeg");

            project.create("테스트 제목", "테스트 설명", LocalDateTime.now(), projectImage, user);
            em.persist(project);
            log.info("TEST 초기 프로젝트 데이터 완료");

            Article article = new Article(user, "Test Article", "컨텐츠 Test", profileImage, project);

            // 양방향
            project.addArticle(article);

            em.persist(article);


            Comment comment = new Comment();
            comment.create("Test Comment", LocalDateTime.now(), LocalDateTime.now());

            // 단방향
            comment.setUser(user);
            // 양방향
            article.addComment(comment);

            em.persist(comment);

        }


        public User createUser(String loginId, String password, Gender gender) {
            User user = new User(loginId, password, gender);
            user.setJoinedDate(LocalDateTime.now());

            return user;
        }

        public void createProfile(User user, String nickname, String message, ImageFile imageFile) {

            UserProfile newProfile = new UserProfile(imageFile, nickname, message);
            user.createProfile(newProfile);
        }
    }



}
