package ojh.jongterest.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Subscription(User user, Project project) {
        this.user = user;
        this.project = project;
    }

    public void changeProject(Project project) {
        this.project = project;
    }

    public void deleteProject(Project project) {
        project.getSubscriptions().remove(this);
        this.project = null;
    }
}
