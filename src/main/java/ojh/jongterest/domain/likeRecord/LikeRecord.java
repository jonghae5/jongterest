package ojh.jongterest.domain.likeRecord;

import lombok.Data;
import ojh.jongterest.domain.article.Article;
import ojh.jongterest.domain.user.User;

@Data
public class LikeRecord {

    private Long likeRecordId;
    private User user;
    private Article article;


    public void create(User user, Article article) {
        this.user = user;
        this.article = article;
    }

}
