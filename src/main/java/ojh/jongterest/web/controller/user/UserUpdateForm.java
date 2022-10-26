package ojh.jongterest.web.controller.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateForm {

//    @NotNull
    private Long userId;
//    @NotBlank
//    private String nickName;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String checkPassword;

    private Gender[] genderTypes = Gender.values();

    // 성별
//    @EnumNamePattern(regexp = "MALE|FEMALE")
    private Gender genderType;

    public UserUpdateForm(Long userId, String loginId,  Gender genderType) {
        this.userId = userId;
        this.loginId = loginId;
        this.genderType = genderType;
    }
}
