package ojh.jongterest.web.controller.article;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
public class ArticleForm {

    private String title;
    private MultipartFile articleImage;
//    TODO
    private Long projectId;



    private String content;


}
