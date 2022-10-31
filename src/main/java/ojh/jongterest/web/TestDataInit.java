package ojh.jongterest.web;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.entity.*;
import ojh.jongterest.common.imageFile.ImageFile;
import ojh.jongterest.common.profile.UserProfile;
import ojh.jongterest.web.controller.user.Gender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataInit {

    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.initData();
        initService.initData2();
    }

    /**
     * 테스트용 데이터 서비스
     */
    @RequiredArgsConstructor
    @Component
    @Transactional
    static class InitService {
        private final EntityManager em;


        public void initData2() {

            User user = User.builder()
                    .loginId("123")
                    .password("123")
                    .gender(Gender.MALE)
                    .build();

            User user2 = User.builder()
                    .loginId("1234")
                    .password("123")
                    .gender(Gender.FEMALE)
                    .build();

            ImageFile profileImage = ImageFile.builder()
                    .storeFilePath("default.jpeg")
                    .uploadFilePath("default.jpeg")
                    .build();
            ImageFile profileImage2 = ImageFile.builder()
                    .storeFilePath("default2.jpeg")
                    .uploadFilePath("default2.jpeg")
                    .build();
            ImageFile profileImage3 = ImageFile.builder()
                    .storeFilePath("default3.jpg")
                    .uploadFilePath("default3.jpg")
                    .build();
            ImageFile profileImage4 = ImageFile.builder()
                    .storeFilePath("default3.jpg")
                    .uploadFilePath("default4.jpg")
                    .build();

            createProfile(user, "John", "테스트메세지", profileImage);
            createProfile(user2, "John2", "테스트메세지2", profileImage2);
            em.persist(user);
            em.persist(user2);


            List<User> userList = new ArrayList<>();
            userList.add(user);
            userList.add(user2);

            List<ImageFile> imageFileList = new ArrayList<>();
            imageFileList.add(profileImage);
            imageFileList.add(profileImage2);
            imageFileList.add(profileImage3);
            imageFileList.add(profileImage4);

            log.info("TEST 초기 유저 데이터 완료");


            List<Project> projectList = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                Faker faker = new Faker(new Locale("ko"));

                int imageNumber = (int)(Math.random() * imageFileList.size());
                int userNumber = (int)(Math.random() *userList.size());
                Project project = Project.builder()
                        .title(faker.company().name())
                        .description(faker.company().industry())
                        .projectImage(imageFileList.get(imageNumber))
                        .build();
                project.setUser(userList.get(userNumber));
                em.persist(project);
                projectList.add(project);
            }

            log.info("TEST 초기 프로젝트 데이터 완료");

            List<Article> articleList = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                Faker faker = new Faker(new Locale("ko"));

                int userNumber = (int)(Math.random() * userList.size());
                int imageNumber = (int)(Math.random() * imageFileList.size());
                int projectNumber = (int)(Math.random() * projectList.size());
                Article article = Article.builder()
                        .title(faker.ancient().hero())
                        .content(faker.ancient().primordial())
                        .articleImage(imageFileList.get(imageNumber))
                        .build();

                article.setUser(userList.get(userNumber));

                Project articleProject = projectList.get(projectNumber);
                if (article.getUser().getLoginId() == articleProject.getUser().getLoginId()) {
                    articleProject.addArticle(article);
                    em.persist(article);
                    articleList.add(article);
                }
            }
            log.info("TEST 초기 아티클 데이터 완료");
            List<Comment> commentList = new ArrayList<>();
            for (int i=0; i<100 ; i++) {
                Faker faker = new Faker(new Locale("ko"));
                Comment comment = Comment.builder()
                        .content(faker.animal().name())
                        .build();
                int userNumber = (int)(Math.random() * userList.size());
                int articleNumber = (int)(Math.random() * articleList.size());

                // 단방향
                comment.setUser(userList.get(userNumber));
                // 양방향
                Article commentArticle = articleList.get(articleNumber);
                commentArticle.addComment(comment);
//
                em.persist(comment);
                commentList.add(comment);
            }
            log.info("TEST 초기 댓글 데이터 완료");
            List<Subscription> subscriptionList = new ArrayList<>();
            for (Project project : projectList) {
                int userNumber = (int)(Math.random() * (userList.size()+1));
                if (userNumber == userList.size()) {
                    continue;
                }
                if (userList.get(userNumber).getLoginId() == project.getUser().getLoginId()) {
                    continue;
                }
                Subscription subscription = Subscription.builder()
                        .project(project)
                        .user(userList.get(userNumber))
                        .build();
                project.addSubscription(subscription);
                em.persist(subscription);
                subscriptionList.add(subscription);
            }
            log.info("TEST 초기 구독 데이터 완료");
            List<LikeRecord> likeRecordList = new ArrayList<>();
            for (Article article : articleList) {
                int userNumber = (int)(Math.random() * (userList.size()+1));
                if (userNumber == userList.size()) {
                    continue;
                }
                LikeRecord likeRecord = LikeRecord.builder()
                        .user(userList.get(userNumber))
                        .article(article)
                        .build();
                article.addLikeRecord(likeRecord);
                em.persist(likeRecord);
                likeRecordList.add(likeRecord);
            }

            log.info("TEST 초기 좋아요 데이터 완료");

        }
        public void initData() {
            User user = User.builder()
                    .loginId("123")
                    .password("123")
                    .gender(Gender.MALE)
                    .build();

            User user2 = User.builder()
                    .loginId("1234")
                    .password("123")
                    .gender(Gender.FEMALE)
                    .build();

            ImageFile profileImage = ImageFile.builder()
                    .storeFilePath("default.jpeg")
                    .uploadFilePath("default.jpeg")
                    .build();

            createProfile(user, "John", "테스트메세지", profileImage);
            createProfile(user2, "John2", "테스트메세지2", profileImage);
            log.info("user={}", user);
            em.persist(user);
            em.persist(user2);
            log.info("TEST 초기 유저 데이터 완료");
//
//

            ImageFile projectImage = ImageFile.builder()
                    .storeFilePath("default.jpeg")
                    .uploadFilePath("default.jpeg")
                    .build();

            Project project = Project.builder()
                    .title("테스트 제목")
                    .description("테스트 설명")
                    .projectImage(projectImage)
                    .build();
            project.setUser(user);

            em.persist(project);
            log.info("TEST 초기 프로젝트 데이터 완료");
//
//            Article article = new Article(user, "Test Article", "컨텐츠 Test", projectImage, project);
            Article article = Article.builder()
                    .title("Test Article")
                    .content("컨텐츠 Test")
                    .articleImage(profileImage)
                    .build();
            article.setUser(user);
            // 양방향
            log.info("article={}", article);
            project.addArticle(article);

            em.persist(article);
//
//
            Comment comment = Comment.builder()
                    .content("Test Comment")
                    .build();
//
//
            // 단방향
            comment.setUser(user);
            // 양방향
            article.addComment(comment);
            log.info("comment 넣기");
//
            em.persist(comment);

        }


        public void createProfile(User user, String nickname, String message, ImageFile imageFile) {

            UserProfile newProfile = new UserProfile(imageFile, nickname, message);
            user.createProfile(newProfile);
        }
    }


}
