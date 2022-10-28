package ojh.jongterest.domain.subscription;

import lombok.Data;
import lombok.Getter;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.user.User;

@Data
public class Subscription {
    private Long subscriptionId;
    private User user;
    private Project project;


    public Subscription(User user, Project project) {
        this.user = user;
        this.project = project;
    }
}
