package ojh.jongterest.web;

import lombok.RequiredArgsConstructor;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.UserRepository;
import ojh.jongterest.web.user.Gender;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserRepository userRepository;
//    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        User user = new User("123","123", Gender.MALE);
        user.setJoinedDate(LocalDateTime.now());
        User user2 = new User("dhwhdgo2368","123", Gender.FEMALE);
        user2.setJoinedDate(LocalDateTime.now());
        userRepository.save(user);
        userRepository.save(user2);

    }

}