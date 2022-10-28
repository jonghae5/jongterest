package ojh.jongterest.web.controller.project;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.imageFile.ImageFile;
import ojh.jongterest.domain.project.Project;
import ojh.jongterest.domain.project.ProjectRepository;
import ojh.jongterest.domain.project.ProjectService;
import ojh.jongterest.domain.subscription.SubscriptionService;
import ojh.jongterest.domain.user.User;
import ojh.jongterest.domain.user.profile.ProfileForm;
import ojh.jongterest.file.FileStore;
import ojh.jongterest.web.argumentResolver.Login;
import ojh.jongterest.web.validation.ProfileFormValidator;
import ojh.jongterest.web.validation.ProjectFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final SubscriptionService subscriptionService;
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    private final FileStore fileStore;
    private final ProjectFormValidator projectFormValidator;
    @GetMapping("/list")
    public String ListView(@Login User loginUser, Model model) {

        List<Project> projects = projectService.getProjectList();
        model.addAttribute("projects", projects);
        return "template/project/list";
    }

    @GetMapping("/detail/{projectId}")
    public String detailProjectView(@Login User loginUser, @PathVariable("projectId") Long projectId, Model model) {
        Project findProject = projectRepository.findById(projectId);
        model.addAttribute("project", findProject);

        Boolean isSubscription = subscriptionService.isSubscription(loginUser.getUserId(), projectId);
        log.info("isSubscription={}",isSubscription);
        model.addAttribute("subscription",isSubscription);
        return "template/project/detail";
    }

    @GetMapping("/create")
    public String createProjectForm(@RequestParam(defaultValue = "/", required = false) String redirectURL, @Login User loginUser,
                                    @ModelAttribute("projectForm") ProjectForm projectForm) {
        return "template/project/create";
    }

    @PostMapping("/create")
    public String createProject(@Login User loginUser, @ModelAttribute("projectForm") ProjectForm projectForm, BindingResult bindingResult,
                                HttpSession session, @RequestParam(defaultValue = "/", required = false) String redirectURL) throws IOException {
        projectFormValidator.validate(projectForm, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "template/projects/create";
        }
        ImageFile projectImage = fileStore.storeFile(projectForm.getProjectImage());
        Project createProject = projectService.createProject(loginUser, projectForm.getTitle(), projectForm.getDescription(), projectImage);

        if (!redirectURL.equals("/")) {
            return "redirect:" + redirectURL;
        }
        return "redirect:/projects/detail/" + String.valueOf(createProject.getProjectId());

    }

    @GetMapping("/delete/{projectId}")
    public String deleteProjectView(@Login User loginUser, @PathVariable("projectId") Long projectId) {

        return "redirect:/projects/delete" + String.valueOf(projectId);
    }


    @PostMapping("/delete/{projectId}")
    public String createProject(@Login User loginUser, @PathVariable("projectId") Long projectId) {
        //TODO 로직
        projectService.deleteProject(projectId);


        return "redirect:/projects/list";
    }


    private String redirectUrl(HttpServletRequest request) {
        if (request.getHeader("Referer") != null) {
            return "redirect:" + request.getHeader("Referer");
        } else {
            return "redirect:/";
        }
    }
}
