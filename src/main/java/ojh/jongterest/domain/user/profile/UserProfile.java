package ojh.jongterest.domain.user.profile;

import lombok.Data;
import lombok.Getter;
import ojh.jongterest.domain.imageFile.ImageFile;

import java.awt.*;

@Getter
public class UserProfile {

    private ImageFile profileImage;
    private String nickname;
    private String message;

    public UserProfile(ImageFile profileImage, String nickname, String message) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.message = message;
    }
}
