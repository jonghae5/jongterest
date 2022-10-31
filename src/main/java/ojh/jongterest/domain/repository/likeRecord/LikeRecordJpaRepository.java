package ojh.jongterest.domain.repository.likeRecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.entity.LikeRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LikeRecordJpaRepository implements LikeRecordRepository {
    private final EntityManager em;

    @Override
    public Optional<LikeRecord> findOne(Long likeRecordId) {
        return Optional.ofNullable(em.find(LikeRecord.class, likeRecordId));
    }

    @Override
    public void save(LikeRecord likeRecord) {
        if (likeRecord.getLikeRecordId() == null) {
            em.persist(likeRecord);
        } else {
            em.merge(likeRecord);
        }
    }

    @Override
    public void deleteById(Long likeRecordId) {
        em.createQuery("DELETE FROM LikeRecord l WHERE l.likeRecordId = :id")
                .setParameter("id", likeRecordId).executeUpdate();
    }

    @Override
    public Optional<LikeRecord> findByArticleIdAndUserId(Long articleId, Long userId) {
        List<LikeRecord> likeRecords = em.createQuery("select l from LikeRecord l " +
                        "join fetch l.article a " +
                        "join fetch l.user u " +
                        "where a.articleId=:articleId and u.userId =: userId ", LikeRecord.class)
                .setParameter("articleId", articleId)
                .setParameter("userId", userId)
                .getResultList();
        return likeRecords.stream().findAny();
    }

    @Override
    public List<LikeRecord> findByArticleId(Long articleId) {
        return em.createQuery("select l from LikeRecord l " +
                        "join fetch l.article a " +
                        "WHERE a.articleId = :id", LikeRecord.class)
                .setParameter("id", articleId).getResultList();

    }

    @Override
    public List<LikeRecord> finByUserId(Long userId) {
        return em.createQuery("select l from LikeRecord l " +
                        "join fetch l.user u " +
                        "WHERE u.userId = :id", LikeRecord.class)
                .setParameter("id", userId).getResultList();
    }

    @Override
    public void delete(LikeRecord likeRecord) {
        em.remove(likeRecord);
    }
}
