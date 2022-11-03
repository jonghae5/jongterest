package ojh.jongterest.file;


import ojh.jongterest.common.imageFile.ImageFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public ImageFile storeFile(MultipartFile multipartFile) throws IOException
    {
        // 없으면 기본 이미지 생성
        if (multipartFile.isEmpty()) {
            return null;
//            String originalDefaultFilename = "";
//            String fullPathDefaultFilename = getFullPath(originalDefaultFilename);
//            multipartFile.transferTo(new File(fullPathDefaultFilename));
//            return new ImageFile(originalDefaultFilename, originalDefaultFilename);
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new ImageFile(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
