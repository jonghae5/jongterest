package ojh.jongterest.domain.entity;

import lombok.*;
import ojh.jongterest.domain.BaseTimeEntity;
import ojh.jongterest.web.controller.user.Gender;
import ojh.jongterest.common.profile.UserProfile;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
//    @Setter
    @Id @GeneratedValue
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //MYSQL 전용 id autoIcrement
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
    @Builder.Default
    private UserProfile profile = new UserProfile();



    public void update(String loginId, String password, Gender gender) {
        this.loginId = loginId;
        this.password = password;
        this.gender = gender;
    }


    public void updateProfile(UserProfile userProfile) {
        this.profile = userProfile;
    }
}
