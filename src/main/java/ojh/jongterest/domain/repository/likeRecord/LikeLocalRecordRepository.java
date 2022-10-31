package ojh.jongterest.domain.repository.likeRecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.entity.LikeRecord;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//@Repository
@RequiredArgsConstructor
@Slf4j
public class LikeLocalRecordRepository implements LikeRecordRepository {
    private static final ConcurrentHashMap<Long, LikeRecord> likeRecordRepository = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, Long> likeRecordUserRepository = new ConcurrentHashMap<>();
    private static final MultiValueMap<Long, Long> articleLikeRecordRepository = new LinkedMultiValueMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);


    @Override
    public Optional<LikeRecord> findOne(Long likeId) {
        return Optional.ofNullable(likeRecordRepository.get(likeId));
    }

    @Override
    public void save(LikeRecord likeRecord) {
        likeRecord.setLikeRecordId(sequence.addAndGet(1));
        likeRecordRepository.put(sequence.get(), likeRecord);
        articleLikeRecordRepository.add(likeRecord.getArticle().getArticleId(), likeRecord.getLikeRecordId());
        likeRecordUserRepository.put(likeRecord.getLikeRecordId(), likeRecord.getUser().getUserId());
    }

    @Override
    public void deleteById(Long likeRecordId) {

        LikeRecord findLikeRecord = findOne(likeRecordId).get();
        List<Long> findLikeRecordIds = articleLikeRecordRepository.get(findLikeRecord.getArticle().getArticleId());
        findLikeRecordIds.remove(likeRecordId);

        likeRecordUserRepository.remove(likeRecordId);
        likeRecordRepository.remove(likeRecordId);

    }

    @Override
    public Optional<LikeRecord> findByArticleIdAndUserId(Long articleId, Long userId) {
        Optional<List<Long>> likeRecordIds = Optional.ofNullable(articleLikeRecordRepository.get(articleId));
        if (likeRecordIds.isEmpty()) {
            return Optional.empty();
        }
        for (Long likeRecordId : likeRecordIds.get()) {
            Long findUserId = likeRecordUserRepository.get(likeRecordId);

            if (findUserId == userId) {
                return findOne(likeRecordId);
            }
        }
        return Optional.empty();
    }


    @Override
    public List<LikeRecord> findByArticleId(Long articleId) {
        return new ArrayList<>();
    }

    @Override
    public List<LikeRecord> finByUserId(Long userId) {
        return null;
    }

    @Override
    public void delete(LikeRecord likeRecord) {

    }

}
