package ojh.jongterest.web.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.user.UserService;
import ojh.jongterest.web.user.UserCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreateValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreateForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserCreateForm userCreateForm = (UserCreateForm) target;

        if (!userService.findUser(userCreateForm.getLoginId()).isEmpty()) {
            errors.reject("idFail", "이미 존재하는 아이디입니다.");
        }
        if (!userCreateForm.getPassword().equals(userCreateForm.getCheckPassword())) {
            errors.reject("passwordFail", "비밀번호가 일치하지 않습니다.");
        }
        if (userCreateForm.getGenderType() == null) {
            errors.rejectValue("genderType", "required.genderType");
        }

    }
}
