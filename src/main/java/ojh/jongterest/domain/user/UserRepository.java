package ojh.jongterest.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    User update(User user);
    User findById(Long userId);
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByNickName(String nickName);
    List<User> findAll();
    void deleteById(Long userId);
    void clearStore();

}
