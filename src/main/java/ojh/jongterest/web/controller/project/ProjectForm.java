package ojh.jongterest.web.controller.project;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProjectForm {


    private MultipartFile projectImage;

    private String title;
    private String description;

}
