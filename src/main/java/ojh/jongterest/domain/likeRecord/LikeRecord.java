package ojh.jongterest.domain.likeRecord;

import lombok.Data;
import lombok.NoArgsConstructor;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
public class LikeRecord {

    @Id @GeneratedValue
    private Long likeRecordId;
    @ManyToOne
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;


    public void create(User user, Article article) {
        this.user = user;
        this.article = article;
    }

    public void changeArticle(Article article) {
        this.article = article;
    }
}
