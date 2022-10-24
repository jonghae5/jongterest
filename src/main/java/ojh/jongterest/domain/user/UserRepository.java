package ojh.jongterest.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository {

    private static final Map<Long, User> userRepository = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong(0);

    public User save(User user) {
        long seq = sequence.addAndGet(1);
        user.setUserId(seq);
        user.setJoinedDate(LocalDateTime.now());

        userRepository.put(seq, user);
        return user;
    }

    public User update(User user) {
        log.info("userId={}",user.getUserId());
        userRepository.put(user.getUserId(), user);
        return user;
    }

    public User findById(Long userId) {
        return userRepository.get(userId);
    }

    public Optional<User> findByLoginId(String loginId) {
        List<User> users = findAll();

        return users.stream()
                .filter(u -> u.getLoginId().equals(loginId))
                .findFirst();
    }

    public Optional<User> findByNickName(String nickName) {
        List<User> users = findAll();

        return users.stream()
                .filter(u -> u.getLoginId().equals(nickName))
                .findFirst();
    }

    public List<User> findAll() {
        return new ArrayList<>(userRepository.values());
    }

    public void deleteById(Long userId) {
        userRepository.remove(userId);
    }
    public void clearStore() {
        userRepository.clear();
    }


}
