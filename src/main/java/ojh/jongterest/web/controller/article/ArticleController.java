package ojh.jongterest.web.controller.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.article.repository.ArticleRepository;
import ojh.jongterest.domain.article.ArticleService;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.project.repository.ProjectRepository;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.UserService;
import ojh.jongterest.file.FileStore;
import ojh.jongterest.web.argumentResolver.Login;
import ojh.jongterest.web.controller.comment.CommentForm;
import ojh.jongterest.web.validation.ArticleCreateFormValidator;
import ojh.jongterest.web.validation.ArticleUpdateFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final ProjectRepository projectRepository;
    private final ArticleService articleService;
    private final FileStore fileStore;
    private final ArticleCreateFormValidator articleCreateFormValidator;
    private final ArticleUpdateFormValidator articleUpdateFormValidator;
    @GetMapping("/list")
    public String listView(Model model) {

        model.addAttribute("articles", articleService.getArticleList());
        return "template/articles/list";
    }


    @GetMapping("/create")
    public String createArticleForm(@Login User loginUser, @ModelAttribute("article") ArticleForm articleForm, Model model) {
        List<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);
        return "template/articles/create";
    }

    @PostMapping("/create")
    public String createArticle(@Login User loginUser, @ModelAttribute("article") ArticleForm articleForm, BindingResult bindingResult,
                                Model model,HttpServletRequest request,
                                @RequestParam(defaultValue = "/") String requestURL) throws IOException {

        articleCreateFormValidator.validate(articleForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            List<Project> projects = projectRepository.findAll();
            model.addAttribute("projects", projects);
            return "template/articles/create";
        }

        Article newArticle = articleService.saveArticle(loginUser, articleForm);

        if (!requestURL.equals("/")) {
            return "redirect:" + requestURL;
        }
        return  "redirect:/articles/detail/" + String.valueOf(newArticle.getArticleId());
    }




    @GetMapping("/detail/{articleId}")
    public String getDetailArticle(@Login User loginUser, @PathVariable("articleId") Long articleId, Model model) {
        Article article = articleRepository.findOne(articleId).get();
        model.addAttribute("article", article);
        model.addAttribute("commentForm", new CommentForm());
        return "template/articles/detail";
    }


    @GetMapping("/update/{articleId}")
    private String updateArticleForm(@Login User loginUser, @PathVariable("articleId") Long articleId, Model model,
                                     HttpServletRequest request) {
        Article findArticle = articleRepository.findOne(articleId).get();

        if (loginUser.getUserId() != findArticle.getUser().getUserId()) {
            redirectUrl(request);
        }

        model.addAttribute("article", findArticle);
        return "template/articles/update";
    }


    @PostMapping("/update/{articleId}")
    public String updateArticle(@Login User loginUser, @PathVariable("articleId") Long articleId,
                                @ModelAttribute("article") ArticleForm articleForm,
                                BindingResult bindingResult,
                                HttpServletRequest request) throws IOException {

        Article findArticle = articleRepository.findOne(articleId).get();

        if (loginUser.getUserId() != findArticle.getUser().getUserId()) {
            redirectUrl(request);
        }

        articleUpdateFormValidator.validate(articleForm, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "template/articles/update";
        }


        log.info("updateArticle 실행");

        articleService.updateArticle(findArticle, articleForm);

        return "redirect:/articles/detail/" + String.valueOf(articleId);
    }



    @GetMapping("/delete/{articleId}")
    private String deleteArticleForm(@Login User loginUser, @PathVariable("articleId") Long articleId, Model model,
                                     HttpServletRequest request) {
        Article findArticle = articleRepository.findOne(articleId).get();

        if (loginUser.getUserId() != findArticle.getUser().getUserId()) {
            redirectUrl(request);
        }

        model.addAttribute("article", findArticle);
        return "template/articles/delete";
    }

    @PostMapping("/delete/{articleId}")
    private String deleteArticle(@Login User loginUser, @PathVariable("articleId") Long articleId, Model model,
                                     HttpServletRequest request) {


        Article findArticle = articleRepository.findOne(articleId).get();

        if (loginUser.getUserId() != findArticle.getUser().getUserId()) {
            redirectUrl(request);
        }
        log.info("deleteArticle 실행");
        articleService.deleteArticle(findArticle);


        return "redirect:/articles/list";
    }


    private String redirectUrl(HttpServletRequest request) {
        if (request.getHeader("Referer") != null) {
            return "redirect:" + request.getHeader("Referer");
        } else {
            return "redirect:/";
        }
    }


}
