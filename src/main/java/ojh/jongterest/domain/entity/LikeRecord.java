package ojh.jongterest.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ojh.jongterest.domain.entity.Article;
import ojh.jongterest.domain.entity.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


    @Builder
    public LikeRecord(User user, Article article) {
        this.user = user;
        this.article = article;
    }

    public void changeArticle(Article article) {
        this.article = article;
    }
}
