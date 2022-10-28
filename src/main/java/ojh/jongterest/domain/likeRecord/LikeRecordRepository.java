package ojh.jongterest.domain.likeRecord;

import java.util.List;
import java.util.Optional;

public interface LikeRecordRepository {
    LikeRecord findById(Long likeId);

    LikeRecord save(LikeRecord likeRecord);
    void delete(Long likeRecordId);


    Optional<Long> findByArticleIdAndUserId(Long articleId,Long userId);
}
