package ojh.jongterest.domain.repository.comment;

import ojh.jongterest.domain.entity.Comment;

import java.util.List;

public interface CommentRepositoryOld {

    Comment save(Long userId, Long articleId, Comment comment);
    Comment update(Long commentId, String content);
    Comment findOne(Long commentId);
    List<Comment> findAll();
    void delete(Long articleId, Long commentId);
}
