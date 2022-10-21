package ojh.jongterest.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.web.user.Gender;
import ojh.jongterest.domain.user.UserRepository;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.web.user.UserCreateForm;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signUp(UserCreateForm userCreateForm) {

        String nickname = userCreateForm.getNickName();
        String loginId = userCreateForm.getLoginId();
        String password = userCreateForm.getPassword();
        Gender genderType = userCreateForm.getGenderType();

        User newUser = new User(nickname, loginId, password, genderType);

        userRepository.save(newUser);
    }

    public User login(String loginId, String password) {
        Optional<User> findUser = userRepository.findByLoginId(loginId);

        return userRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public Optional<User> findUser(String loginId) {
        return userRepository.findByLoginId(loginId);
    }
}
