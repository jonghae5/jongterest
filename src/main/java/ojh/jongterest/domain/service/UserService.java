package ojh.jongterest.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.entity.Article;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.domain.repository.article.ArticleRepository;
import ojh.jongterest.domain.entity.Comment;
import ojh.jongterest.domain.repository.comment.CommentRepository;
import ojh.jongterest.common.imageFile.ImageFile;
import ojh.jongterest.domain.entity.LikeRecord;
import ojh.jongterest.domain.repository.likeRecord.LikeRecordRepository;
import ojh.jongterest.domain.entity.Project;
import ojh.jongterest.domain.repository.project.ProjectRepository;
import ojh.jongterest.domain.entity.Subscription;
import ojh.jongterest.domain.repository.subscription.SubscriptionRepository;
import ojh.jongterest.common.profile.UserProfile;
import ojh.jongterest.domain.repository.user.UserRepository;
import ojh.jongterest.web.controller.user.Gender;
import ojh.jongterest.web.controller.user.UserCreateForm;
import ojh.jongterest.web.controller.user.UserUpdateForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ProjectRepository projectRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final LikeRecordRepository likeRecordRepository;
    private final CommentRepository commentRepository;
    private final ProjectService projectService;

    @Transactional
    public void signUp(UserCreateForm userCreateForm) {
        User newUser = createFormToUserConverter(userCreateForm);
        userRepository.save(newUser);
    }

    public User login(String loginId, String password) {
        Optional<User> findUser = userRepository.findByLoginId(loginId);

        return userRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }
    public User findUserByUserId(Long userId) {
        return userRepository.findOne(userId).get();
    }

    public Optional<User> findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    public Optional<User> findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }


    @Transactional
    public User updateUser(Long userId, UserUpdateForm userUpdateForm) {

        User updateUser = userRepository.findOne(userId).get();

        String loginId = userUpdateForm.getLoginId();
        String password = userUpdateForm.getPassword();
        Gender genderType = userUpdateForm.getGenderType();

        updateUser.update(loginId,password,genderType);

        // Dirty Checking
//        userRepository.update(updateUser);
        return updateUser;
    }



    @Transactional
    public void deleteUser(User user) {

        User findUser = findUserByUserId(user.getUserId());
        List<Project> projects = projectRepository.findByUserId(findUser.getUserId());
        // 나 자신의 프로젝트
        if (projects.size()>0) {
            for (Project project : projects) {
                log.info("project 실행");
                List<Article> articles = articleRepository.findByProjectId(project.getProjectId());
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
                        project.deleteArticle(article);
                        articleRepository.delete(article);
                    }
                }
                List<Subscription> subscriptions = project.getSubscriptions();
                if (subscriptions.size() > 0) {
                    project.deleteSubscriptions(subscriptions);
                    for (Subscription subscription : subscriptions) {
                        subscriptionRepository.delete(subscription);
                    }
                }
                projectRepository.deleteById(project.getProjectId());
            }
        }

        // 다른 프로젝트에 구독한 구독 삭제
        List<Subscription> userSubscriptions = subscriptionRepository.findByUserId(user.getUserId());
        if (userSubscriptions.size() > 0) {
            for (Subscription subscription : userSubscriptions) {
                subscription.deleteProject(subscription.getProject());
                subscriptionRepository.delete(subscription);
            }
        }

        // 다른 게시글에 작성한 댓글 삭제
        List<Comment> userComments = commentRepository.findByUserId(user.getUserId());
        if (userComments.size() > 0) {
//                            article.deleteComments(comments);
            for (Comment userComment : userComments) {
                commentRepository.delete(userComment);
            }
        }

        // 다른 게시글에 좋아요 삭제
        List<LikeRecord> userLikeRecords = likeRecordRepository.finByUserId(user.getUserId());
        if (userLikeRecords.size() > 0) {
            for (LikeRecord userLikeRecord : userLikeRecords) {
                likeRecordRepository.delete(userLikeRecord);
            }
        }

        userRepository.deleteById(findUser.getUserId());
    }


    @Transactional
    public User createUserProfile(User user, String nickname, String message, ImageFile profileImage) {

        user.createProfile(new UserProfile(profileImage, nickname, message));
        // 해줘야함 영속성 컨텍스트에 올라가있지 않음.
        userRepository.update(user);
        return user;
    }

    private User createFormToUserConverter(UserCreateForm userCreateForm) {

        String loginId = userCreateForm.getLoginId();
        String password = userCreateForm.getPassword();
        Gender genderType = userCreateForm.getGenderType();

        User user = User.builder()
                .loginId(loginId)
                .password(password)
                .gender(genderType)
                .build();
        return user;
    }

}