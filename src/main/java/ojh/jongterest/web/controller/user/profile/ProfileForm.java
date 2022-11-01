package ojh.jongterest.web.controller.user.profile;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileForm {

    private MultipartFile profileImage;


    @NotBlank
    @Size(max = 20, message = "{ProfileForm.nickname.error}")
    private String nickname;

    @NotBlank
    @Size(max = 20, message = "{ProfileForm.message.error}")
    private String message;

    @Builder
    public ProfileForm(MultipartFile profileImage, String nickname, String message) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.message = message;
    }
}
