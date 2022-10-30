package ojh.jongterest.domain.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ojh.jongterest.domain.comment.Comment;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.likeRecord.LikeRecord;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Article {
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

    @Embedded
    private ImageFile articleImage;


    //양방향
    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    private String content;

    @Setter
    @OneToMany(mappedBy = "article")
    private List<LikeRecord> likeRecords = new ArrayList<>();
    //양방향
    @Setter
    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();

    public Article(User user, String title, String content, ImageFile articleImage, Project project) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.articleImage = articleImage;
        this.project = project;
    }

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

