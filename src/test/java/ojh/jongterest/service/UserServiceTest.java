package ojh.jongterest.service;

import ojh.jongterest.domain.user.UserService;
import ojh.jongterest.web.controller.user.Gender;
import ojh.jongterest.domain.user.UserRepository;
import ojh.jongterest.web.login.LoginForm;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.web.controller.user.UserCreateForm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
    
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    void signUp() {
        User user1 = new User("hello", "id", "password", Gender.MALE);
        UserCreateForm userCreateForm = new UserCreateForm("id","password",Gender.FEMALE);
        userService.signUp(userCreateForm);
        User findUser = userService.findUserByLoginId(user1.getLoginId()).get();
        Assertions.assertThat(findUser.getLoginId()).isEqualTo(user1.getLoginId());
    }

    @Test
    void login() {
        User user1 = new User("hello", "id", "password", Gender.MALE);
        UserCreateForm userCreateForm = new UserCreateForm("id","password",Gender.FEMALE);
        userService.signUp(userCreateForm);

        LoginForm loginForm = new LoginForm("id", "password");
        User loginUser = userService.login(loginForm.getLoginId(),loginForm.getPassword());
        Assertions.assertThat(loginUser.getLoginId()).isEqualTo(user1.getLoginId());
    }
}