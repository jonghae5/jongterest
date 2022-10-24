package ojh.jongterest.domain.user.profile;

import lombok.Data;

@Data
public class ProfileImage {

    private String uploadFilePath;
    private String storeFilePath;

    public ProfileImage(String uploadFilePath, String storeFilePath) {
        this.uploadFilePath = uploadFilePath;
        this.storeFilePath = storeFilePath;
    }
}
