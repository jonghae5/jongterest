package ojh.jongterest.web.user;

import lombok.Data;

import ojh.jongterest.web.validator.EnumNamePattern;

import javax.validation.constraints.NotBlank;

@Data
public class UserCreateForm {

    @NotBlank
    private String nickName;
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

    public UserCreateForm(String nickName, String loginId, String password, String checkPassword, Gender genderType) {
        this.nickName = nickName;
        this.loginId = loginId;
        this.password = password;
        this.checkPassword = checkPassword;
        this.genderType = genderType;
    }
}
