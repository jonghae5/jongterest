package ojh.jongterest.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.entity.Article;
import ojh.jongterest.domain.entity.Comment;
import ojh.jongterest.domain.repository.article.ArticleRepository;
import ojh.jongterest.domain.repository.comment.CommentRepository;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.domain.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void saveComment(Long userId, Long articleId, String commentContent) {
        Comment newComment = Comment.builder()
                .content(commentContent)
                .build();
        User findUser = userRepository.findOne(userId).get();
        newComment.setUser(findUser);

        Article findArticle = articleRepository.findOne(articleId).get();
        findArticle.addComment(newComment);
        commentRepository.save(newComment);

    }
    @Transactional
    public void deleteComment(Long articleId, Long commentId) {
        Optional<Comment> findComment = commentRepository.findOne(commentId);

        if (findComment.isPresent()) {
        Article findArticle = articleRepository.findOne(articleId).get();
        findArticle.getComments().remove(findComment.get());
        commentRepository.delete(findComment.get());
        }
    }

    @Transactional
    public void updateComment(Long commentId, String content) {

        Optional<Comment> findComment = commentRepository.findOne(commentId);
        if (findComment.isPresent()) {
            findComment.get().update(content);
            //안해줘도됨
            commentRepository.update(findComment.get());
        }

    }
}
