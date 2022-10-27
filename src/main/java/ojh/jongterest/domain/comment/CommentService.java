package ojh.jongterest.domain.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.article.ArticleRepository;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public Comment saveComment(Long userId, Long articleId, String commentContent) {
        Comment newComment = new Comment(commentContent, LocalDateTime.now(), LocalDateTime.now());
        log.info("saveComment<Service>");
        Comment saveComment = commentRepository.save(userId, articleId, newComment);
        User findUser = userRepository.findById(userId);
        saveComment.setUser(findUser);

        Article findArticle = articleRepository.findById(articleId);
        log.info("findArticle={}",findArticle);

        findArticle.getComments().add(saveComment);
        return saveComment;
    }

    public void deleteComment(Long articleId, Long commentId) {
        Article findArticle = articleRepository.findById(articleId);
        findArticle.getComments().remove(commentRepository.findById(commentId));
        commentRepository.delete(articleId, commentId);
    }

    public void updateComment(Long articleId, Long commentId, String content) {
        commentRepository.update(articleId, commentId, content);
    }
}
