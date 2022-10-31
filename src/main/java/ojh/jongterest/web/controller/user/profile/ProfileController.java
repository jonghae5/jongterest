package ojh.jongterest.web.controller.user.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.common.imageFile.ImageFile;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.domain.service.UserService;
import ojh.jongterest.common.profile.ProfileForm;
import ojh.jongterest.common.profile.UserProfile;
import ojh.jongterest.file.FileStore;
import ojh.jongterest.web.argumentResolver.Login;
import ojh.jongterest.web.validation.ProfileFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class ProfileController {

    private final UserService userService;
    private final FileStore fileStore;
    private final ProfileFormValidator profileFormValidator;

    @GetMapping("/create/{usedId}")
    public String createProfileForm(@ModelAttribute("profile") ProfileForm profileForm) {
        return "/template/user/profile/create";
    }

    @PostMapping("/create/{usedId}")
    public String createProfile(@RequestParam(defaultValue = "/", required = false) String redirectURL, @Login User loginUser, @ModelAttribute("profile") ProfileForm profileForm, BindingResult bindingResult, HttpSession session) throws IOException {
        profileFormValidator.validate(profileForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "template/articles/create";
        }

        ImageFile profileImage = fileStore.storeFile(profileForm.getProfileImage());
        User createUser = userService.createUserProfile(loginUser, profileForm.getNickname(), profileForm.getMessage(), profileImage);
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
        UserProfile loginUserProfile = loginUser.getProfile();
        ProfileForm profileForm = new ProfileForm(loginUserProfile.getNickname(), loginUserProfile.getMessage());
        model.addAttribute("profile", profileForm);
        return "/template/user/profile/update";
    }

    @PostMapping("/update/{userId}")
    public String updateProfile(@Login User loginUser, @PathVariable("userId") Long userId,
                                @ModelAttribute("profile") ProfileForm profileForm, BindingResult bindingResult,HttpSession session) throws IOException {
        profileFormValidator.validate(profileForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "template/articles/create";
        }

        ImageFile profileImage =  fileStore.storeFile(profileForm.getProfileImage());
        User updateUser = userService.createUserProfile(loginUser, profileForm.getNickname(), profileForm.getMessage(), profileImage);
//        session.setAttribute(SessionConst.LOGIN_USER, updateUser);
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
