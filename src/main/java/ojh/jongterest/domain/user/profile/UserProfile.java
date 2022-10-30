package ojh.jongterest.domain.user.profile;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ojh.jongterest.domain.imageFile.ImageFile;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.awt.*;

@Getter
@Embeddable
@NoArgsConstructor
public class UserProfile {
    @Embedded
    private ImageFile profileImage;
    @Column(name = "nickname")
    private String nickname;
    private String message;

    public UserProfile(ImageFile profileImage, String nickname, String message) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.message = message;
    }
}
