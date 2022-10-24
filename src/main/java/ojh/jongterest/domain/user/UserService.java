package ojh.jongterest.domain.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.user.profile.ProfileImage;
import ojh.jongterest.domain.user.profile.UserProfile;
import ojh.jongterest.web.user.Gender;
import ojh.jongterest.web.user.UserCreateForm;
import ojh.jongterest.web.user.UserUpdateForm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User signUp(UserCreateForm userCreateForm) {
        User newUser = createFormToUserConverter(userCreateForm);
        return userRepository.save(newUser);
    }

    public User login(String loginId, String password) {
        Optional<User> findUser = userRepository.findByLoginId(loginId);

        return userRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }
    public User findUserByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    public Optional<User> findUserByNickName(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    public User updateUser(Long userId, UserUpdateForm userUpdateForm) {

        User updateUser = userRepository.findById(userId);

        String loginId = userUpdateForm.getLoginId();
        String password = userUpdateForm.getPassword();
        Gender genderType = userUpdateForm.getGenderType();

        updateUser.update(loginId,password,genderType);

        userRepository.update(updateUser);
        return updateUser;
    }




    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);

    }

    public User createUserProfile(User user, String nickname, String message, ProfileImage profileImage) {
        user.createProfile(new UserProfile(profileImage, nickname, message));
        return user;
    }

    private User createFormToUserConverter(UserCreateForm userCreateForm) {

        String loginId = userCreateForm.getLoginId();
        String password = userCreateForm.getPassword();
        Gender genderType = userCreateForm.getGenderType();

        User user = new User(loginId, password, genderType);
        return user;
    }

}
