package ojh.jongterest.web;

import lombok.RequiredArgsConstructor;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.UserRepository;
import ojh.jongterest.web.user.Gender;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
        User user = new User("John","123","123", Gender.MALE);
        userRepository.save(user);

    }

}