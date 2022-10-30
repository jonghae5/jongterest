package ojh.jongterest.domain.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.subscription.Subscription;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.web.controller.article.ArticleForm;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Project {

    @Setter
    @Id @GeneratedValue
    private Long projectId;

    @Setter
    @Embedded
    private ImageFile projectImage;

    private String title;
    private String description;
    private LocalDateTime createdDate;

    //    @OneToMany(mappedBy = "project")
    @OneToMany(mappedBy = "project")
    private List<Article> articles = new ArrayList<>();

    @Setter
    @OneToOne(fetch = LAZY)
    @JoinColumn(name="USER_ID")
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "project")
    List<Subscription> subscriptions = new ArrayList<>();

    public void create( String title, String description, LocalDateTime createdDate, ImageFile projectImage , User user) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.projectImage = projectImage;
        this.user = user;
    }

    public void addArticle(Article article) {
        this.articles.add(article);
        article.changeProject(this);
    }

    public void deleteArticle(Article article) {
        articles.remove(article);
        article.changeProject(null);
    }


    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
        subscription.changeProject(this);
    }

    public void deleteSubscriptions(List<Subscription> subscriptions) {
        for (Subscription subscription : subscriptions) {
            subscription.changeProject(null);
        }
        subscriptions.clear();
    }
}
