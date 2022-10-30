package ojh.jongterest.domain.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Entity
@NoArgsConstructor
public class Comment {
    @Setter
    @Id @GeneratedValue
    @Column(name = "COMMENT_ID", insertable=false, updatable=false)
    private Long commentId;
    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "USER_ID")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;


    public void create(String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public void update(String content, LocalDateTime modifiedDate) {
        this.content = content;
        this.modifiedDate = modifiedDate;
    }


    public void changeArticle(Article article) {
        this.article = article;
    }
}
