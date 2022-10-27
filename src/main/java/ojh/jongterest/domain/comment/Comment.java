package ojh.jongterest.domain.comment;

import lombok.Getter;
import lombok.Setter;
import ojh.jongterest.domain.user.User;

import java.time.LocalDateTime;
@Getter
public class Comment {
    @Setter
    private Long commentId;
    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Setter
    private User user;


    public Comment(String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public void update(String content, LocalDateTime modifiedDate) {
        this.content = content;
        this.modifiedDate = modifiedDate;
    }
}
