package ojh.jongterest.domain.repository;

import ojh.jongterest.domain.repository.user.UserLocalRepository;
import ojh.jongterest.web.controller.user.Gender;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.domain.repository.user.UserRepositoryOld;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest {

    private static UserRepositoryOld userRepository = new UserLocalRepository();

    @AfterEach
    void afterEach() {
        userRepository.clearStore();
    }


    @Test
    void findById() {
        User user1 = new User("id", "password", Gender.MALE);
        userRepository.save(user1);

        User findUser = userRepository.findOne(1L);
//        assertThat(findUser.getNickName()).isEqualTo(user1.getNickName());
    }
    @Test
    void findAll() {
        User user1 = new User("id", "password", Gender.MALE);
        User user2 = new User("id", "password", Gender.FEMALE);

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> all = userRepository.findAll();

        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    void findByLoginId() {
        User user1 = new User("id", "password",  Gender.MALE);

        userRepository.save(user1);

        User findUser = userRepository.findByLoginId("id").get();

//        assertThat(findUser.getNickName()).isEqualTo(user1.getNickName());

    }
}

