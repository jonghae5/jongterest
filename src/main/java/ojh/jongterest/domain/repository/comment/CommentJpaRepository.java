package ojh.jongterest.domain.repository.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.entity.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentJpaRepository implements CommentRepository {

    private final EntityManager em;

    @Override
    public void save(Comment comment) {
        if (comment.getCommentId() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }


    @Override
    public void update(Comment comment) {
        if (comment.getCommentId() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }

    @Override
    public Optional<Comment> findOne(Long commentId) {
        return Optional.ofNullable(em.find(Comment.class, commentId));
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class).getResultList();
    }

    @Override
    public void delete(Comment comment) {
        em.createQuery("DELETE FROM Comment c WHERE c.commentId = :id")
                .setParameter("id", comment.getCommentId()).executeUpdate();
    }

    @Override
    public List<Comment> findByUserId(Long userId) {
        return em.createQuery("SELECT c FROM Comment c " +
                        "join fetch c.user u " +
                        "where u.userId =:id", Comment.class)
                .setParameter("id", userId)
                .getResultList();

    }
}
