package ojh.jongterest.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ojh.jongterest.common.imageFile.ImageFile;
import ojh.jongterest.domain.BaseTimeEntity;
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
public class Article extends BaseTimeEntity {
    @Setter
    @Id @GeneratedValue
    private Long articleId;

    //단방향
    @Setter
    @ManyToOne(fetch = LAZY)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "USER_ID")
    private User user;

    private String title;

    private String content;
    @Embedded
    private ImageFile articleImage;

    //양방향
    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private Project project;


//    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "article")
    private List<LikeRecord> likeRecords = new ArrayList<>();
    //양방향
    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();


    public void update(String title, String content, ImageFile articleImage) {
        this.title = title;
        this.content = content;
        this.articleImage = articleImage;
    }

    public void setProject(Project findProject) {
        this.project = findProject;
        findProject.getArticles().add(this);
    }

    public void changeProject(Project project) {
        this.project = project;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.changeArticle(this);
    }

    public void addLikeRecord(LikeRecord likeRecord) {
        likeRecords.add(likeRecord);
        likeRecord.changeArticle(this);
    }


}

