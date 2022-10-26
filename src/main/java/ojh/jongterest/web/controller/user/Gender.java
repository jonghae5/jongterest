package ojh.jongterest.web.controller.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum Gender {
    MALE("남성"),
    FEMALE("여성");

    private String genderType;

    Gender(String genderType) {
        this.genderType = genderType;
    }

}
