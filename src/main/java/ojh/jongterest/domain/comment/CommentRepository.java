package ojh.jongterest.domain.comment;

import java.util.List;

public interface CommentRepository {

    Comment save(Long userId, Long articleId, Comment comment);
    Comment findById(Long commentId);
    List<Comment> findAll();
    void delete(Long articleId, Long commentId);
    Comment update(Long articleId, Long commentId, String content);
}
