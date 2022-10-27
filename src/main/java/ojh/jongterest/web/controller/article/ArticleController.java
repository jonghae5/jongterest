package ojh.jongterest.web.controller.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.article.ArticleRepository;
import ojh.jongterest.domain.article.ArticleService;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.UserService;
import ojh.jongterest.file.FileStore;
import ojh.jongterest.web.argumentResolver.Login;
import ojh.jongterest.web.controller.comment.CommentForm;
import ojh.jongterest.web.validation.ArticleFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;
    private final FileStore fileStore;
    private final ArticleFormValidator articleFormValidator;
    @GetMapping("/list")
    public String getList(Model model) {

        model.addAttribute("articles", articleService.getArticleList());
        return "template/article/list";
    }


    @GetMapping("/create")
    public String createArticleForm(@Login User loginUser, @ModelAttribute("article") ArticleForm articleForm) {
        return "template/article/create";
    }

    @PostMapping("/create")
    public String createArticle(@Login User loginUser, @ModelAttribute("article") ArticleForm articleForm, BindingResult bindingResult) throws IOException {

        articleFormValidator.validate(articleForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "template/article/create";
        }

        ImageFile articleImage =  fileStore.storeFile(articleForm.getArticleImage());
        Article newArticle = new Article(loginUser, articleForm.getTitle(), articleForm.getContent(), articleImage);

        articleService.save(loginUser.getUserId(), newArticle);

        return  "redirect:/article/detail/" + String.valueOf(newArticle.getArticleId());
    }




    @GetMapping("/detail/{articleId}")
    public String getDetailArticle(@Login User loginUser, @PathVariable("articleId") Long articleId, Model model) {
        Article article = articleRepository.findById(articleId);
        model.addAttribute("article", article);
        model.addAttribute("commentForm", new CommentForm());
        return "template/article/detail";
    }


    @GetMapping("/update/{articleId}")
    private String updateArticleForm(@Login User loginUser, @PathVariable("articleId") Long articleId, Model model,
                                     HttpServletRequest request) {
        Article findArticle = articleRepository.findById(articleId);

        if (loginUser.getUserId() != findArticle.getUser().getUserId()) {
            redirectUrl(request);
        }

        model.addAttribute("article", findArticle);
        return "template/article/update";
    }


    @PostMapping("/update/{articleId}")
    public String updateArticle(@Login User loginUser, @PathVariable("articleId") Long articleId,
                                @ModelAttribute("article") ArticleForm articleForm,
                                BindingResult bindingResult,
                                HttpServletRequest request) throws IOException {

        Article findArticle = articleRepository.findById(articleId);
        if (loginUser.getUserId() != findArticle.getUser().getUserId()) {
            redirectUrl(request);
        }
        articleFormValidator.validate(articleForm, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "template/article/update";
        }

        ImageFile articleImage =  fileStore.storeFile(articleForm.getArticleImage());
        findArticle.updateArticle(articleForm.getTitle(), articleForm.getContent(), articleImage);
        articleRepository.update(findArticle);

        return "redirect:/article/detail/" + String.valueOf(articleId);
    }



    @GetMapping("/delete/{articleId}")
    private String deleteArticleForm(@Login User loginUser, @PathVariable("articleId") Long articleId, Model model,
                                     HttpServletRequest request) {
        Article findArticle = articleRepository.findById(articleId);

        if (loginUser.getUserId() != findArticle.getUser().getUserId()) {
            redirectUrl(request);
        }

        model.addAttribute("article", findArticle);
        return "template/article/delete";
    }

    @PostMapping("/delete/{articleId}")
    private String deleteArticle(@Login User loginUser, @PathVariable("articleId") Long articleId, Model model,
                                     HttpServletRequest request) {
        Article findArticle = articleRepository.findById(articleId);

        if (loginUser.getUserId() != findArticle.getUser().getUserId()) {
            redirectUrl(request);
        }

        articleRepository.delete(loginUser.getUserId(),articleId);

        return "redirect:/article/list";
    }


    private String redirectUrl(HttpServletRequest request) {
        if (request.getHeader("Referer") != null) {
            return "redirect:" + request.getHeader("Referer");
        } else {
            return "redirect:/";
        }
    }


}
