package ojh.jongterest.domain.article;

import lombok.Getter;
import lombok.Setter;
import ojh.jongterest.domain.comment.Comment;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Article {
    @Setter
    private Long articleId;
    @Setter
    private User user;

    private String title;

    private ImageFile articleImage;
    private Project project;

    private String content;

    private Integer likeRecord = 0;
    @Setter
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

    public void addLike(){
        likeRecord +=1;
    }
    public void removeLike(){
        if (likeRecord>0) {
            likeRecord -=1;
        }
    }
}

