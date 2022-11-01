package ojh.jongterest.common.profile;

import lombok.*;
import ojh.jongterest.common.imageFile.ImageFile;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    @Embedded
    private ImageFile profileImage;
    @Column(name = "nickname")
    private String nickname;
    private String message;


    public void update(String nickname, String message, ImageFile profileImage) {
        this.nickname = nickname;
        this.message = message;
        this.profileImage = profileImage;
    }
}
