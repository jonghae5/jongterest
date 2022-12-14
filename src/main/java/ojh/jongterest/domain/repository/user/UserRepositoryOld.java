package ojh.jongterest.domain.repository.user;

import ojh.jongterest.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryOld {

    void save(User user);
    User update(User user);
    User findOne(Long userId);
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByNickName(String nickName);
    List<User> findAll();
    void deleteById(Long userId);
    void clearStore();

}
