package ojh.jongterest.web.user;

import lombok.Data;

import ojh.jongterest.web.validator.EnumNamePattern;

import javax.validation.constraints.NotBlank;

@Data
public class UserCreateForm {

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

    public UserCreateForm(String loginId, String password, Gender genderType) {
        this.loginId = loginId;
        this.password = password;

        this.genderType = genderType;
    }
}