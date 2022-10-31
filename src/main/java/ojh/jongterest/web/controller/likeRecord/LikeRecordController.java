package ojh.jongterest.web.controller.likeRecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.repository.likeRecord.LikeRecordRepository;
import ojh.jongterest.domain.service.LikeRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LikeRecordController {

    private final LikeRecordService likeRecordService;
    private final LikeRecordRepository likeRecordRepository;

    @PostMapping("/like/{articleId}/{userId}")
    public String updateLikeRecord(@PathVariable("articleId") Long articleId,@PathVariable("userId") Long userId, HttpServletRequest request) {
        likeRecordService.updateLikeRecord(articleId, userId);
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
