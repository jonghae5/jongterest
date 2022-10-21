package ojh.jongterest.web.home;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.web.argumentResolver.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home(@Login User loginUser, Model model) {

        if (loginUser != null) {
            model.addAttribute("user", loginUser);
        }

        return "template/home";
    }
}
