package ojh.jongterest.web.controller.project;

import lombok.Data;
import lombok.Setter;
import ojh.jongterest.domain.imageFile.ImageFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class ProjectForm {


    private MultipartFile projectImage;

    private String title;
    private String description;

}
