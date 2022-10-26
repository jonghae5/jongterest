package ojh.jongterest.domain.article;

import lombok.Getter;
import lombok.Setter;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.user.User;

@Getter
public class Article {
    @Setter
    private Long articleId;
    @Setter
    private User user;

    private String title;

    private ImageFile articleImage;
//    TODO
//    private Project project;

    private String content;

    public Article(User user, String title, String content, ImageFile articleImage) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.articleImage = articleImage;
    }

    public void updateArticle(String title, String content, ImageFile articleImage) {
        this.title = title;
        this.content = content;
        this.articleImage = articleImage;


    }
}

