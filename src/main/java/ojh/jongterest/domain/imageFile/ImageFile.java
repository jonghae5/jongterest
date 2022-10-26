package ojh.jongterest.domain.imageFile;

import lombok.Data;

@Data
public class ImageFile {
    private String uploadFilePath;
    private String storeFilePath;

    public ImageFile(String uploadFilePath, String storeFilePath) {
        this.uploadFilePath = uploadFilePath;
        this.storeFilePath = storeFilePath;
    }
}
