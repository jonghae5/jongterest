package ojh.jongterest.web.controller.subscription;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.article.ArticleService;
import ojh.jongterest.domain.subscription.SubscriptionService;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.web.argumentResolver.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {


//    private ArticleService articleService;
    private final SubscriptionService subscriptionService;
    private final ArticleService articleService;

    @GetMapping("/list")
    public String subscriptionListView(@Login User loginUser, Model model) {
        List<Article> articles = articleService.findArticlesWithUserProjectArticleContainingUser(loginUser);
        model.addAttribute("articles",articles);
        return "template/subscription/list";
    }

    @PostMapping("/subscribe/{projectId}")
    public String subscribeProject(@PathVariable("projectId") Long projectId, @Login User loginUser,
                                   HttpServletRequest request){

        log.info("subscription subscribeProject 실행");
        subscriptionService.saveSubscription(loginUser.getUserId(),projectId);
        return redirectUrl(request);
    }

    @PostMapping("/unsubscribe/{projectId}")
    public String unsubscribeProject(@PathVariable("projectId") Long projectId, @Login User loginUser,
                                   HttpServletRequest request){
        log.info("subscription unsubscribeProject  실행");
        subscriptionService.deleteSubscription(loginUser.getUserId(),projectId);
        return redirectUrl(request);
    }


    private String redirectUrl(HttpServletRequest request) {
        if (request.getHeader("Referer") != null) {
            return "redirect:" + request.getHeader("Referer");
        } else {
            return "redirect:/";
        }
    }
}
