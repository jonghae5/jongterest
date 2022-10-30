package ojh.jongterest.domain.user.repository;


import lombok.RequiredArgsConstructor;
import ojh.jongterest.domain.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserJpaRepository implements UserRepository{

    private final EntityManager em;

    @Override
    public void save(User user) {
        if (user.getUserId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }
    @Override
    public void update(User user) {
        if (user.getUserId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    @Override
    public Optional<User> findOne(Long userId) {
        return Optional.ofNullable(em.find(User.class, userId));
    }


    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public void deleteById(Long userId) {
        Optional<User> findUser = findOne(userId);
        if (findUser.isPresent()) {
            em.remove(findUser.get());
        }

    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        List<User> users = em.createQuery("select u from User u where u.loginId = :loginId", User.class)
                .setParameter("loginId", loginId).getResultList();
        return users.stream().findAny();

    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        List<User> users = em.createQuery("select u from User u where u.nickname = :nickname", User.class)
                .setParameter("nickname", nickname).getResultList();

        return users.stream().findAny();
    }

}
