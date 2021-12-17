package rs.ac.uns.ftn.vehicleinspectionapp.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class ImageUtil {

    private static String UPLOADS_PATH = "src/main/resources/uploads/";

    public static String save(MultipartFile multipartFile) {
        String newFilename = UUID.randomUUID().toString();
        String extension = ImageUtil.getExtension(multipartFile.getOriginalFilename());
        String target = UPLOADS_PATH + newFilename + extension;
        try {
            Files.copy(multipartFile.getInputStream(), Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Image could not be saved. Error: " + e.getMessage());
        }

        return target;
    }

    public static String getExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }

}
