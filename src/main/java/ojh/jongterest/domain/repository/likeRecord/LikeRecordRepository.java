package ojh.jongterest.domain.repository.likeRecord;

import ojh.jongterest.domain.entity.Comment;
import ojh.jongterest.domain.entity.LikeRecord;

import java.util.List;
import java.util.Optional;

public interface LikeRecordRepository {
    Optional<LikeRecord> findOne(Long likeId);

    void save(LikeRecord likeRecord);
    void deleteById(Long likeRecordId);


    Optional<LikeRecord> findByArticleIdAndUserId(Long articleId,Long userId);

    List<LikeRecord> findByArticleId(Long articleId);

    List<LikeRecord> finByUserId(Long userId);

    void delete(LikeRecord likeRecord);
}
