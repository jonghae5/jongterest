package ojh.jongterest.web.controller.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserCreateForm {



    @Pattern(regexp="[a-zA-Z1-9]{6,12}", message = "{loginId.error}")
    private String loginId;

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "{password.error}")
    private String password;
    @NotBlank
    private String checkPassword;

    private Gender[] genderTypes = Gender.values();

    // 성별
//    @EnumNamePattern(regexp = "MALE|FEMALE")
    @NotNull(message = "{gender.error}")
    private Gender genderType;

    public UserCreateForm(String loginId, String password, Gender genderType) {
        this.loginId = loginId;
        this.password = password;

        this.genderType = genderType;
    }
}
