package ojh.jongterest.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ojh.jongterest.domain.user.profile.UserProfile;
import ojh.jongterest.web.controller.user.Gender;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Setter
    @Id @GeneratedValue
    private Long userId;

    private String loginId;
    private String password;

    @Setter
    private LocalDateTime joinedDate;
    // 현재 로그인 되어 있는지
    private Boolean isActive;
    // 성별

    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 프로필
    @Embedded
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
