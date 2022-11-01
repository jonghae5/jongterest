package ojh.jongterest.web.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.domain.service.UserService;
import ojh.jongterest.web.controller.project.ProjectForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectFormValidator implements Validator {

    private final UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return ProjectForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProjectForm projectForm = (ProjectForm) target;

//        if (projectForm.getTitle() == null || projectForm.getTitle().isEmpty()) {
//            errors.reject("titleFail", "제목을 입력해주세요.");
//        }
//
//        if (projectForm.getDescription() == null || projectForm.getDescription().isEmpty()) {
//            errors.reject("contentFail", "설명을 입력해주세요.");
//        }

//        if (projectForm.getProjectImage().isEmpty()) {
//            errors.reject("imageFail", "이미지를 넣어주세요.");
//        }



    }
}
