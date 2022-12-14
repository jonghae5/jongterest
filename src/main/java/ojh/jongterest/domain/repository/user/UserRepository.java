package ojh.jongterest.domain.repository.user;

import ojh.jongterest.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);
    void update(User user);
    Optional<User> findOne(Long userId);
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByNickname(String nickName);
    List<User> findAll();
    void deleteById(Long userId);

}
