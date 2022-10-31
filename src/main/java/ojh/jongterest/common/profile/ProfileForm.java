package ojh.jongterest.common.profile;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileForm {

    private MultipartFile profileImage;
    private String nickname;
    private String message;

    public ProfileForm(String nickname, String message) {
        this.nickname = nickname;
        this.message = message;
    }
}
