package ojh.jongterest.web.controller.article;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ArticleForm {

    private String title;
    private MultipartFile articleImage;
//    TODO
//    private Project project;
    private String content;

}
