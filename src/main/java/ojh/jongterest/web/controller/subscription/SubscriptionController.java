package ojh.jongterest.web.controller.subscription;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.common.Pagination.Pagination;
import ojh.jongterest.domain.entity.Article;
import ojh.jongterest.domain.service.ArticleService;
import ojh.jongterest.domain.service.SubscriptionService;
import ojh.jongterest.domain.entity.User;
import ojh.jongterest.web.argumentResolver.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String subscriptionListView(@Login User loginUser, Model model,
                                       @RequestParam(value = "page", defaultValue = "1", required = false) int page) {
        Pagination pagination = new Pagination();
        pagination.create(page);
        List<Article> articles = articleService.findArticlesWithUserProjectArticleContainingUserOrderByUpdatedAt(loginUser, page);
        model.addAttribute("pagination", pagination);
        model.addAttribute("articles",articles);
        return "template/subscription/list";
    }

    @PostMapping("/subscribe/{projectId}")
    public String subscribeProject(@PathVariable("projectId") Long projectId, @Login User loginUser,
                                   HttpServletRequest request){

        subscriptionService.saveSubscription(loginUser.getUserId(),projectId);
        return redirectUrl(request);
    }

    @PostMapping("/unsubscribe/{projectId}")
    public String unsubscribeProject(@PathVariable("projectId") Long projectId, @Login User loginUser,
                                   HttpServletRequest request){
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
