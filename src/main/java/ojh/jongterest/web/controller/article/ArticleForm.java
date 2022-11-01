package ojh.jongterest.web.controller.article;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class ArticleForm {

    private Long projectId;
    @NotBlank
//    @NotBlank(message = "제목을 입력하세요.")
    @Size(max = 20, message = "{ArticleForm.title.error}")
    private String title;

    @NotBlank
//    @NotBlank(message = "내용을 입력하세요.")
    @Size(max = 200, message = "{ArticleForm.content.error}")
    private String content;

    private MultipartFile articleImage;

}
