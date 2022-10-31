package ojh.jongterest.domain.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.entity.Article;
import ojh.jongterest.domain.entity.LikeRecord;
import ojh.jongterest.domain.repository.article.ArticleRepository;
import ojh.jongterest.domain.repository.likeRecord.LikeRecordRepository;
import ojh.jongterest.domain.repository.project.ProjectRepository;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.domain.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            LikeRecord likeRecord = LikeRecord.builder()
                    .user(user)
                    .build();
            // 양방향
            article.addLikeRecord(likeRecord);
            likeRecordRepository.save(likeRecord);
        }

    }
}
