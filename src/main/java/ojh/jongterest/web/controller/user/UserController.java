package ojh.jongterest.web.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.entity.Project;
import ojh.jongterest.domain.repository.project.ProjectRepository;
import ojh.jongterest.domain.service.ProjectService;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.domain.repository.user.UserRepository;
import ojh.jongterest.domain.service.UserService;
import ojh.jongterest.web.argumentResolver.Login;
import ojh.jongterest.web.session.SessionConst;
import ojh.jongterest.web.validation.UserCreateFormValidator;
import ojh.jongterest.web.validation.UserUpdateFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    private final UserCreateFormValidator userCreateFormValidator;
    private final UserUpdateFormValidator userUpdateFormValidator;


    @GetMapping("/create")
    public String createUserForm(@ModelAttribute("user") UserCreateForm userCreateForm, Model model) {

        return "template/user/create";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("user") UserCreateForm userCreateForm, BindingResult bindingResult,
                             @RequestParam(defaultValue = "/") String requestURL,HttpSession session) {

        userCreateFormValidator.validate(userCreateForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "template/user/create";
        }

        User user = userService.signUp(userCreateForm);
        session.setAttribute(SessionConst.LOGIN_USER, user);
        return "redirect:" + requestURL;
    }


    @GetMapping("/detail/{userId}")
    public String DetailUserView(@PathVariable Long userId, Model model) {
        User findUser = userRepository.findOne(userId).get();

        List<Project> projects = projectRepository.findByUserId(userId);
        List<Project> subscriptionProjects = projectRepository.findAllWithSubscriptionsContainingUser(findUser);
        model.addAttribute("projects", projects);
        model.addAttribute("subscription_projects", subscriptionProjects);
        model.addAttribute("user", findUser);
        return "template/user/detail";
    }

    @GetMapping("/update/{userId}")
    public String updateUserForm(@Login User loginUser, @PathVariable Long userId, Model model, HttpServletRequest request) {

        if (loginUser.getUserId() != userId) {
            redirectUrl(request);
        }

        UserUpdateForm userUpdateForm = new UserUpdateForm(loginUser.getUserId(),loginUser.getLoginId(),loginUser.getGender());
        model.addAttribute("user", userUpdateForm);

        return "template/user/update";
    }

    @PostMapping("/update/{userId}")
    public String updateUser(@Login User loginUser, @Valid @ModelAttribute("user") UserUpdateForm userUpdateForm,
                             BindingResult bindingResult, HttpSession session) {

        userUpdateFormValidator.validate(userUpdateForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "template/user/update";
        }

        userService.updateUser(loginUser.getUserId(), userUpdateForm);
//        session.setAttribute(SessionConst.LOGIN_USER, updateUser);

        return "redirect:/user/detail/" + String.valueOf(loginUser.getUserId());

    }

    @GetMapping("/delete/{userId}")
    public String deleteUserForm(@PathVariable Long userId) {
        return "template/user/delete";
    }

    @PostMapping("/delete/{userId}")
    public String deleteUser(@Login User loginUser, @PathVariable Long userId,HttpSession session) {

        userService.deleteUser(loginUser);

        //세션 삭제
        session.removeAttribute(SessionConst.LOGIN_USER);
        session.invalidate();
        return "redirect:/";
    }


    private String redirectUrl(HttpServletRequest request) {
        if (request.getHeader("Referer") != null) {
            return "redirect:" + request.getHeader("Referer");
        } else {
            return "redirect:/";
        }
    }
}
