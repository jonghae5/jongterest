package ojh.jongterest.web.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.service.UserService;
import ojh.jongterest.web.controller.user.UserUpdateForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUpdateFormValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserUpdateForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserUpdateForm userCreateForm = (UserUpdateForm) target;

        if (!userService.findUserByUserId(userCreateForm.getUserId()).getLoginId().equals(userCreateForm.getLoginId())) {
            errors.reject("idFail", "아이디는 변경할 수 없습니다.");
        }

//        if (!userService.findUserByNickName(userCreateForm.getNickName()).isEmpty()) {
//            errors.reject("nickNameFail", "이미 존재하는 닉네입니다.");
//        }

        if (!userCreateForm.getPassword().equals(userCreateForm.getCheckPassword())) {
            errors.reject("passwordFail", "비밀번호가 일치하지 않습니다.");
        }
//        if (userCreateForm.getGenderType() == null) {
//            errors.rejectValue("genderType", "required.genderType");
//        }

    }
}
