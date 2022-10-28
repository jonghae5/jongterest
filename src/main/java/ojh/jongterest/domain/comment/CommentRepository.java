package ojh.jongterest.domain.comment;

import java.util.List;

public interface CommentRepository {

    Comment save(Long userId, Long articleId, Comment comment);
    Comment update(Long commentId, String content);
    Comment findById(Long commentId);
    List<Comment> findAll();
    void delete(Long articleId, Long commentId);
}
