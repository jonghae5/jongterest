package ojh.jongterest.web.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.user.UserRepository;
import ojh.jongterest.domain.user.UserService;
import ojh.jongterest.web.validation.UserCreateValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserCreateValidator userCreateValidator;


    @GetMapping("/create")
    public String createUserForm(@ModelAttribute("user") UserCreateForm userCreateForm, Model model) {

        return "template/user/create";
    }

    @PostMapping("/create")
    public String createUser(@Validated @ModelAttribute("user") UserCreateForm userCreateForm, BindingResult bindingResult) {

        userCreateValidator.validate(userCreateForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "template/user/create";
        }

        userService.signUp(userCreateForm);
        return "redirect:/";
    }




    @GetMapping("/detail/{userId}")
    public String createUser(@PathVariable Long userId) {
        return "template/user/detail";
    }
    @GetMapping("/update/{userId}")
    public String updateUser(@PathVariable Long userId) {
        return "template/user/update";
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        return "template/user/delete";
    }
}
