package ojh.jongterest.web.controller.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.entity.Article;
import ojh.jongterest.domain.repository.article.ArticleRepository;
import ojh.jongterest.domain.service.CommentService;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.web.argumentResolver.Login;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final ArticleRepository articleRepository;
    private final CommentService commentService;
    @PostMapping("/create/{articleId}")
    public String createComment(@Login User loginUser, @PathVariable("articleId") Long articleId,
                                @Valid @ModelAttribute("commentForm") CommentForm commentForm,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "redirect:/articles/detail/" + String.valueOf(articleId);
        }

        log.info("CREATE COMMENT 실행 <Controller>");
        commentService.saveComment(loginUser.getUserId(), articleId, commentForm.getContent());

        return "redirect:/articles/detail/" + String.valueOf(articleId);
    }

    @PostMapping("/delete/{articleId}/{commentId}")
    public String deleteComment(@Login User loginUser,
                              @PathVariable("articleId") Long articleId,@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(articleId, commentId);

        return "redirect:/articles/detail/" + String.valueOf(articleId);
    }

    @PostMapping("/update/{articleId}/{commentId}")
    public String updateComment(@Login User loginUser,
                                @PathVariable("articleId") Long articleId, @PathVariable("commentId") Long commentId,
                                @Valid @ModelAttribute("form") CommentForm form, BindingResult bindingResult,
                                HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "articles/detail/" + String.valueOf(articleId);
        }

        Article article = articleRepository.findOne(articleId).get();
        if (loginUser.getUserId() != article.getUser().getUserId()) {
            redirectUrl(request);
        }

        commentService.updateComment(commentId, form.getContent());

        return "redirect:/articles/detail/" + String.valueOf(articleId);

    }

    private String redirectUrl(HttpServletRequest request) {
        if (request.getHeader("Referer") != null) {
            return "redirect:" + request.getHeader("Referer");
        } else {
            return "redirect:/";
        }
    }
}
