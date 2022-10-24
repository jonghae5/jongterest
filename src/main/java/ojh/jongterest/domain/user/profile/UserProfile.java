package ojh.jongterest.domain.user.profile;

import lombok.Data;
import lombok.Getter;

@Getter
public class UserProfile {

    private ProfileImage  profileImage;
    private String nickname;
    private String message;

    public UserProfile(ProfileImage profileImage, String nickname, String message) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.message = message;
    }
}
