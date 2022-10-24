package ojh.jongterest.web.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.UserService;
import ojh.jongterest.domain.user.profile.ProfileForm;
import ojh.jongterest.web.user.UserCreateForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileFormValidator implements Validator {

    private final UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return ProfileForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProfileForm profileForm = (ProfileForm) target;

        List<User> users = userService.findUsers();
        for (User user : users) {
            if (user.getProfile()!=null) {
                if(user.getProfile().getNickname().equals(profileForm.getNickname())) {
                    errors.rejectValue("nickname", "nickname.error", "이미 존재하는 닉네입니다.");
                }
            }
        }
//        Optional<User> findUser = users.stream()
//                .filter(m -> m.getProfile().getNickname().equals(profileForm.getNickname()))
//                .findFirst();
//
//        if (!findUser.isEmpty()) {
//            errors.rejectValue("nickname", "nickname.error", "이미 존재하는 닉네입니다.");
//        }


    }
}
