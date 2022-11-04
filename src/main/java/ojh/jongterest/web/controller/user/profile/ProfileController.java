package ojh.jongterest.web.controller.user.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.common.imageFile.ImageFile;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.domain.repository.user.UserRepository;
import ojh.jongterest.domain.service.UserService;
import ojh.jongterest.file.FileStore;
import ojh.jongterest.web.argumentResolver.Login;
import ojh.jongterest.web.validation.ProfileCreateFormValidator;
import ojh.jongterest.web.validation.ProfileUpdateFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final FileStore fileStore;
    private final ProfileCreateFormValidator profileCreateFormValidator;
    private final ProfileUpdateFormValidator profileUpdateFormValidator;

    @GetMapping("/create/{usedId}")
    public String createProfileForm(@ModelAttribute("profile") ProfileForm profileForm) {
        return "template/user/profile/create";
    }

    @PostMapping("/create/{usedId}")
    public String createProfile(@RequestParam(defaultValue = "/", required = false) String redirectURL, @Login User loginUser,
                                @Valid @ModelAttribute("profile") ProfileForm profileForm, BindingResult bindingResult,
                                HttpSession session) throws IOException {

        profileCreateFormValidator.validate(profileForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "template/user/profile/create";
        }
        log.debug("전 create profile loginUser={}",loginUser.getProfile());
        userService.createUserProfile(loginUser, profileForm);
//        session.setAttribute(SessionConst.LOGIN_USER, createUser);

        if (!redirectURL.equals("/")) {
            return "redirect:" + redirectURL;
        }
        return "redirect:/user/detail/" + String.valueOf(loginUser.getUserId());
    }


    @GetMapping("/update/{userId}")
    public String updateProfileForm(@Login User loginUser, @PathVariable("userId") Long userId, Model model, HttpServletRequest request) {
        if (loginUser.getUserId() != userId) {
            redirectUrl(request);
        }
        Optional<User> findUser = userRepository.findOne(loginUser.getUserId());
        if (findUser.isPresent()) {
            model.addAttribute("profile", findUser.get().getProfile());
        }
        return "template/user/profile/update";
    }

    @PostMapping("/update/{userId}")
    public String updateProfile(@Login User loginUser, @PathVariable("userId") Long userId,
                                @Valid @ModelAttribute("profile") ProfileForm profileForm, BindingResult bindingResult,HttpSession session,
                                HttpServletRequest request) throws IOException {


        if (loginUser.getUserId() != userId) {
            redirectUrl(request);
        }

        profileUpdateFormValidator.validate(profileForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "template/user/profile/update";
        }
        userService.updateUserProfile(loginUser, profileForm);

        return "redirect:/user/detail/" + String.valueOf(loginUser.getUserId());
    }

    private String redirectUrl(HttpServletRequest request) {
        if (request.getHeader("Referer") != null) {
            return "redirect:" + request.getHeader("Referer");
        } else {
            return "redirect:/";
        }
    }
}
