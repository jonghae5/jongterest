package ojh.jongterest.web.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojh.jongterest.web.controller.article.ArticleForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleUpdateFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ArticleForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ArticleForm articleForm = (ArticleForm) target;

//        if (articleForm.getTitle() == null || articleForm.getTitle().isEmpty()) {
//            errors.reject("titleFail", "제목을 입력해주세요.");
//        }
//
//        if (articleForm.getContent() == null || articleForm.getContent().isEmpty()) {
//            errors.reject("contentFail", "내용을 입력해주세요.");
//        }

//        if (articleForm.getProjectId() == 0) {
//            errors.reject("projectFail", "프로젝트를 선택 해주세요.");
//        }

//        if (articleForm.getArticleImage().isEmpty()) {
//            errors.reject("imageFail", "이미지를 넣어주세요.");
//        }


    }
}
