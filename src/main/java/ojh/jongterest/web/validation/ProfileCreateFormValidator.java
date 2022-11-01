package ojh.jongterest.web.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.domain.repository.user.UserRepository;
import ojh.jongterest.domain.service.UserService;
import ojh.jongterest.web.controller.user.profile.ProfileForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileCreateFormValidator implements Validator {

    private final UserRepository userRepository;
    private final UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return ProfileForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProfileForm profileForm = (ProfileForm) target;

        Optional<User> findUser = userRepository.findByNickname(profileForm.getNickname());
        if (findUser.isPresent()) {
            if (!(findUser.get().getProfile().getNickname().equals(profileForm.getNickname()))) {
                errors.rejectValue("nickname", "nickname.error", "이미 존재하는 닉네입니다.");
            }
        }

        if (profileForm.getProfileImage().isEmpty()) {
            errors.reject("imageFail", "이미지를 넣어주세요.");
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
