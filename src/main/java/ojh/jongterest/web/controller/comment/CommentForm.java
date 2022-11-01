package ojh.jongterest.web.controller.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CommentForm {
//    @NotBlank(message = "댓글을 입력하세요.")
    @NotBlank
    @Size(max = 30, message = "{CommentForm.content.error}")
    private String content;
}
