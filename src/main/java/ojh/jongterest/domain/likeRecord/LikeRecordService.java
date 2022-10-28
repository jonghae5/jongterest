package ojh.jongterest.domain.likeRecord;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.article.ArticleRepository;
import ojh.jongterest.domain.project.ProjectRepository;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeRecordService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ArticleRepository articleRepository;
    private final LikeRecordRepository likeRecordRepository;

    public void updateLikeRecord(Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId);
        User user = userRepository.findById(userId);

        Optional<Long> likeRecordId = likeRecordRepository.findByArticleIdAndUserId(articleId, userId);
        if (likeRecordId.isEmpty()) {
            LikeRecord likeRecord = new LikeRecord();
            likeRecord.create(user, article);
            likeRecordRepository.save(likeRecord);
            article.addLike();
        } else {
            likeRecordRepository.delete(likeRecordId.get());
            article.removeLike();
        }





    }
}
