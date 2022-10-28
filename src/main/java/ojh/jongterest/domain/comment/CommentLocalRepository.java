package ojh.jongterest.domain.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentLocalRepository implements CommentRepository {

    // article, comments
    private static final MultiValueMap<Long, Long> articleCommentRepository = new LinkedMultiValueMap<>();
    // comment user
    private static final ConcurrentHashMap<Long, Long> commentUserRepository = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<Long, Comment> commentRepository = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong(0);

    @Override
    public Comment save(Long userId, Long articleId, Comment comment) {
        comment.setCommentId(sequence.addAndGet(1));
        log.info("commentId={}",sequence.get());
        commentRepository.put(sequence.get(), comment);

        commentUserRepository.put(sequence.get(), userId);
        articleCommentRepository.add(articleId, comment.getCommentId());
        log.info("articleId={}",articleId);
        log.info("comment.getCommentId()={}",comment.getCommentId());
        return comment;
    }

    @Override
    public Comment findById(Long commentId) {
        return  commentRepository.get(commentId);
    }

    @Override
    public List<Comment> findAll() {
        List<Long> commentIds = articleCommentRepository.values().stream().flatMap(m -> m.stream())
                .collect(Collectors.toList());

        ArrayList<Comment> comments = new ArrayList<>();
        for (Long commentId : commentIds) {
            Comment findComment = findById(commentId);
            comments.add(findComment);
        }
        if (comments.isEmpty()) {
            return Collections.emptyList();
        }
        return comments;
    }

    @Override
    public void delete(Long articleId, Long commentId) {
        Comment findComment = findById(commentId);
        log.info("findComment={}", findComment);
        log.info("articleId={}", articleId);
        log.info("commentId={}", commentId);
        List<Long> findCommentIds = articleCommentRepository.get(articleId);
        log.info("findCommentIds={}", findCommentIds);
        List<Long> filteredCommentIds = findCommentIds.stream().filter(v -> v != commentId).collect(Collectors.toList());

        articleCommentRepository.replace(articleId, filteredCommentIds);
        commentRepository.remove(commentId);
        commentUserRepository.remove(commentId);

    }

    @Override
    public Comment update(Long commentId, String content) {
        Comment findComment = findById(commentId);
        findComment.update(content, LocalDateTime.now());

        return findComment;
    }


}
