package ojh.jongterest.domain.likeRecord.repository;

import ojh.jongterest.domain.likeRecord.LikeRecord;

import java.util.List;
import java.util.Optional;

public interface LikeRecordRepository {
    Optional<LikeRecord> findOne(Long likeId);

    void save(LikeRecord likeRecord);
    void deleteById(Long likeRecordId);


    Optional<LikeRecord> findByArticleIdAndUserId(Long articleId,Long userId);

    List<LikeRecord> findByArticleId(Long articleId);
}
