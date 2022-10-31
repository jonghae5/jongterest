package ojh.jongterest.domain.entity;

import lombok.*;
import ojh.jongterest.domain.BaseTimeEntity;
import ojh.jongterest.web.controller.user.Gender;
import ojh.jongterest.common.profile.UserProfile;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
//    @Setter
    @Id @GeneratedValue
    private Long userId;

    private String loginId;
    private String password;


    // 현재 로그인 되어 있는지
    private Boolean isActive;
    // 성별

    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 프로필
    @Embedded
    private UserProfile profile;


    @Builder
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
