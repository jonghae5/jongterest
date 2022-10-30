package ojh.jongterest.domain.subscription;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.user.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Data
@Entity
@NoArgsConstructor
public class Subscription {
    @Id @GeneratedValue
    private Long subscriptionId;

    @ManyToOne(fetch = LAZY)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="PROJECT_ID")
    private Project project;


    public Subscription(User user, Project project) {
        this.user = user;
        this.project = project;
    }

    public void changeProject(Project project) {
        this.project = project;
    }
}
