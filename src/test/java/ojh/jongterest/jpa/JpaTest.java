package ojh.jongterest.jpa;


import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.article.ArticleService;
import ojh.jongterest.domain.article.repository.ArticleJpaRepository;
import ojh.jongterest.domain.article.repository.ArticleRepository;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.project.ProjectService;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.profile.UserProfile;
import ojh.jongterest.domain.user.repository.UserJpaRepository;
import ojh.jongterest.domain.user.repository.UserRepository;
import ojh.jongterest.web.controller.comment.CommentForm;
import ojh.jongterest.web.controller.user.Gender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@Transactional
public class JpaTest {


    @Autowired
    EntityManager em;
    @Autowired
    UserJpaRepository userRepository;

    @Autowired
    ArticleJpaRepository articleRepository;
    @Autowired
    ArticleService articleService;
    @Autowired
    ProjectService projectService;

    @Test
    void 테스트3() {
//        List<Article> result = articleRepository.findByUserId(1L);
        User user = new User("123","123", Gender.MALE);
        user.setJoinedDate(LocalDateTime.now());

        em.createQuery("select a from Article a join fetch a.user u join fetch a.project p join fetch p.subscriptions where a.user.userId =: id", Article.class)
                .setParameter("id",1L)
                .getResultList();
//        articleRepository.findAllWithUserProjectArticleContainingUser(user);
    }




}
