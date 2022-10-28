package ojh.jongterest.domain.project;

import lombok.Getter;
import lombok.Setter;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.web.controller.article.ArticleForm;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Project {

    @Setter
    private Long projectId;
    @Setter
    private ImageFile projectImage;

    private String title;
    private String description;
    private LocalDateTime createdDate;

    private List<Article> articles = new ArrayList<>();
    @Setter
    private User user;

    public Project create( String title, String description, LocalDateTime createdDate, ImageFile projectImage , User user) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.projectImage = projectImage;
        this.user = user;

        return this;
    }


}
