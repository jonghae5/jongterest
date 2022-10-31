package ojh.jongterest.domain.entity;

import lombok.*;
import ojh.jongterest.domain.BaseTimeEntity;
import ojh.jongterest.domain.entity.Article;
import ojh.jongterest.common.imageFile.ImageFile;
import ojh.jongterest.domain.entity.Subscription;
import ojh.jongterest.domain.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseTimeEntity {

    @Setter
    @Id @GeneratedValue
    private Long projectId;

    @Setter
    @Embedded
    private ImageFile projectImage;

    private String title;
    private String description;


    //    @OneToMany(mappedBy = "project")
    @Builder.Default
    @OneToMany(mappedBy = "project")
    private List<Article> articles = new ArrayList<>();

    @Setter
    @OneToOne(fetch = LAZY)
    @JoinColumn(name="USER_ID")
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "project")
    List<Subscription> subscriptions = new ArrayList<>();

    public void addArticle(Article article) {
        articles.add(article);
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
