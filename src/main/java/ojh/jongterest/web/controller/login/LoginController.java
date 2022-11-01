package ojh.jongterest.web.controller.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.web.session.SessionConst;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.domain.service.UserService;
import ojh.jongterest.web.session.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "template/user/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return "template/user/login";
        }

        User loginUser = userService.login(loginForm.getLoginId(),loginForm.getPassword());
        //로그인 에러 체크
        loginErrorCheck(bindingResult, loginUser);

        if (bindingResult.hasErrors()) {
            return "template/user/login";
        }


        //로그인 성공 처리 TODO
        //세션이 있으면 기존 세션 반환, 없으면 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
        //쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 삭제)
        log.info("login User Profile={}", loginUser.getProfile());
        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session!=null) {
            session.removeAttribute(SessionConst.LOGIN_USER);
            session.invalidate();
        }
        return "redirect:/";
    }

    //TODO
    // Login Error CHECK
    private void loginErrorCheck(BindingResult bindingResult, User loginUser) {
        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");

        }
    }
}
