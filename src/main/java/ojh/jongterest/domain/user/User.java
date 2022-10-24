package ojh.jongterest.domain.user;

import lombok.Getter;
import lombok.Setter;
import ojh.jongterest.domain.user.profile.UserProfile;
import ojh.jongterest.web.user.Gender;

import java.time.LocalDateTime;

//@Entity
@Getter
public class User {
    @Setter
    private Long userId;

    private String nickName;
    private String loginId;
    private String password;

    @Setter
    private LocalDateTime joinedDate;
    // 현재 로그인 되어 있는지
    private Boolean isActive;
    // 성별
    private Gender gender;

    // 프로필
    private UserProfile profile;

    public User(String loginId, String password, Gender gender) {
        this.loginId = loginId;
        this.password = password;
        this.gender = gender;
    }


    public void update(String loginId, String password, Gender gender) {
        this.loginId = loginId;
        this.password = password;
        this.gender = gender;
    }

    public void createProfile(UserProfile profile) {
        this.profile = profile;
    }
}
