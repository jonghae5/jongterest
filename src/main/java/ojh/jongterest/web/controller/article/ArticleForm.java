package ojh.jongterest.web.controller.article;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.project.ProjectRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ArticleForm {

    private String title;
    private MultipartFile articleImage;
//    TODO
    private Long projectId;



    private String content;


}
