package ojh.jongterest.web.user.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.UserService;
import ojh.jongterest.domain.user.profile.ProfileForm;
import ojh.jongterest.domain.user.profile.ProfileImage;
import ojh.jongterest.domain.user.profile.UserProfile;
import ojh.jongterest.file.FileStore;
import ojh.jongterest.web.argumentResolver.Login;
import ojh.jongterest.web.session.SessionConst;
import ojh.jongterest.web.validation.ProfileFormValidator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String createProfile(@Login User loginUser, @ModelAttribute("profile") ProfileForm profileForm, BindingResult bindingResult, HttpSession session) throws IOException {
        profileFormValidator.validate(profileForm, bindingResult);
        ProfileImage profileImage = fileStore.storeFile(profileForm.getProfileImage());
        User createUser = userService.createUserProfile(loginUser, profileForm.getNickname(), profileForm.getMessage(), profileImage);
//        session.setAttribute(SessionConst.LOGIN_USER, createUser);

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
        ProfileImage profileImage = fileStore.storeFile(profileForm.getProfileImage());
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
