package ojh.jongterest.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private static final Map<Long, User> userRepository = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong(0);

    public User save(User user) {
        long seq = sequence.addAndGet(1);
        user.setUserId(seq);

        userRepository.put(seq, user);
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
    public List<User> findAll() {
        return new ArrayList<>(userRepository.values());
    }

    public void clearStore() {
        userRepository.clear();
    }
}
