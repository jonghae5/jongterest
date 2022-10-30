package ojh.jongterest.domain.imageFile;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class ImageFile {
    private String uploadFilePath;
    private String storeFilePath;

    public ImageFile(String uploadFilePath, String storeFilePath) {
        this.uploadFilePath = uploadFilePath;
        this.storeFilePath = storeFilePath;
    }
}
