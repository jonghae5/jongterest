package ojh.jongterest.domain.likeRecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.subscription.Subscription;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LikeLocalRecordRepository implements LikeRecordRepository {
    private static final ConcurrentHashMap<Long, LikeRecord> likeRecordRepository = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, Long> likeRecordUserRepository = new ConcurrentHashMap<>();
    private static final MultiValueMap<Long, Long> articleLikeRecordRepository = new LinkedMultiValueMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);

    @Override
    public LikeRecord findById(Long likeId) {
        return likeRecordRepository.get(likeId);
    }

    @Override
    public LikeRecord save(LikeRecord likeRecord) {
        likeRecord.setLikeRecordId(sequence.addAndGet(1));
        likeRecordRepository.put(sequence.get(), likeRecord);
        articleLikeRecordRepository.add(likeRecord.getArticle().getArticleId(), likeRecord.getLikeRecordId());
        likeRecordUserRepository.put(likeRecord.getLikeRecordId(), likeRecord.getUser().getUserId());
        return likeRecord;
    }

    @Override
    public void delete(Long likeRecordId) {

        LikeRecord findLikeRecord = findById(likeRecordId);
        List<Long> findLikeRecordIds = articleLikeRecordRepository.get(findLikeRecord.getArticle().getArticleId());
        findLikeRecordIds.remove(likeRecordId);

        likeRecordUserRepository.remove(likeRecordId);
        likeRecordRepository.remove(likeRecordId);

    }

    @Override
    public Optional<Long> findByArticleIdAndUserId(Long articleId, Long userId) {
        Optional<List<Long>> likeRecordIds = Optional.ofNullable(articleLikeRecordRepository.get(articleId));
        if (likeRecordIds.isEmpty()) {
            return Optional.ofNullable(null);
        }
        for (Long likeRecordId : likeRecordIds.get()) {
            Long findUserId = likeRecordUserRepository.get(likeRecordId);
            if (findUserId == userId) {
                return Optional.of(likeRecordId);
            }
        }
        return Optional.ofNullable(null);
    }



}
