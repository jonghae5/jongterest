package ojh.jongterest.domain.comment.repository;

import ojh.jongterest.domain.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    void save(Comment comment);
    void update(Comment comment);
    Optional<Comment> findOne(Long commentId);
    List<Comment> findAll();
    void delete(Comment comment);
}
