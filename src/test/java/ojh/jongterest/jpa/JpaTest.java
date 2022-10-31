package ojh.jongterest.jpa;


import ojh.jongterest.domain.entity.Article;
import ojh.jongterest.domain.service.ArticleService;
import ojh.jongterest.domain.repository.article.ArticleJpaRepository;
import ojh.jongterest.domain.service.ProjectService;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.domain.repository.user.UserJpaRepository;
import ojh.jongterest.web.controller.user.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;


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

//    @Test
    void 테스트3() {
//        List<Article> result = articleRepository.findByUserId(1L);
        User user = new User("123","123", Gender.MALE);
//        user.setJoinedDate(LocalDateTime.now());

        em.createQuery("select a from Article a join fetch a.user u join fetch a.project p join fetch p.subscriptions where a.user.userId =: id", Article.class)
                .setParameter("id",1L)
                .getResultList();
//        articleRepository.findAllWithUserProjectArticleContainingUser(user);
    }




}
