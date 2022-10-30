package ojh.jongterest.domain.likeRecord;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.article.repository.ArticleRepository;
import ojh.jongterest.domain.likeRecord.repository.LikeRecordRepository;
import ojh.jongterest.domain.project.repository.ProjectRepository;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LikeRecordService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ArticleRepository articleRepository;
    private final LikeRecordRepository likeRecordRepository;

    public void updateLikeRecord(Long articleId, Long userId) {
        Article article = articleRepository.findOne(articleId).get();
        User user = userRepository.findOne(userId).get();

        Optional<LikeRecord> findLikeRecord = likeRecordRepository.findByArticleIdAndUserId(articleId, userId);
        if (findLikeRecord.isPresent()) {
            likeRecordRepository.deleteById(findLikeRecord.get().getLikeRecordId());
        } else {
            LikeRecord likeRecord = new LikeRecord();
            likeRecord.create(user, article);
            article.addLikeRecord(likeRecord);
            likeRecordRepository.save(likeRecord);
        }

    }
}
