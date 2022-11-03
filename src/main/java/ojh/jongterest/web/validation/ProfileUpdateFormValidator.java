package ojh.jongterest.web.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.domain.repository.user.UserRepository;
import ojh.jongterest.domain.service.UserService;
import ojh.jongterest.web.controller.user.profile.ProfileForm;
import ojh.jongterest.web.session.SessionConst;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileUpdateFormValidator implements Validator {

    private final UserRepository userRepository;
    private final UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return ProfileForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProfileForm profileForm = (ProfileForm) target;

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servletRequestAttributes.getRequest().getSession(true);
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        Optional<User> findUser = userRepository.findByNickname(profileForm.getNickname());
        if (findUser.isPresent()) {
            if (!loginUser.getLoginId().equals(findUser.get().getLoginId())) {
                log.info("===Profile Form Validator===");
                log.info("loginUser id={}", loginUser.getLoginId());
                log.info( "findUser id={}", findUser.get().getLoginId());
                errors.rejectValue("nickname", "nickname.error", "이미 존재하는 닉네입니다.");
            }
        }


    }
}
