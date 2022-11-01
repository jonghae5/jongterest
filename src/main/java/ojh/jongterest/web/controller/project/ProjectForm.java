package ojh.jongterest.web.controller.project;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProjectForm {

    private MultipartFile projectImage;
    @NotBlank
//    @NotBlank(message = "프로젝트 제목을 입력하세요")
    @Size(max = 20, message = "{ProjectForm.title.error}")
    private String title;
//    @NotBlank(message = "프로젝트 설명을 입력하세요")
    @NotBlank
    @Size(max = 20, message = "{ProjectForm.description.error}")
    private String description;

}
