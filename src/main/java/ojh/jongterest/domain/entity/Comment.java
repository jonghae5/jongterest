package ojh.jongterest.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ojh.jongterest.domain.BaseTimeEntity;
import ojh.jongterest.domain.entity.Article;
import ojh.jongterest.domain.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {
    @Setter
    @Id @GeneratedValue
    @Column(name = "COMMENT_ID", insertable=false, updatable=false)
    private Long commentId;
    private String content;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "USER_ID")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @Builder
    public Comment(String content, User user, Article article) {
        this.content = content;
        this.user = user;
        this.article = article;
    }

    public void update(String content) {
        this.content = content;
    }


    public void changeArticle(Article article) {
        this.article = article;
    }
}
