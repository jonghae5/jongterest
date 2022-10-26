package ojh.jongterest.domain.article;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    public List<Article> getArticleList() {
        List<Article> all = articleRepository.findAll();

        // 10개만 가져오기
        if (all.size() > 10) {
            return all.subList(0,10);
        }
        return all;



    }
}
