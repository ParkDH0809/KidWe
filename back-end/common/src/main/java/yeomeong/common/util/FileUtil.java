package yeomeong.common.util;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class FileUtil {


    public static String convertFileName(MultipartFile file) throws Exception {
        if(file.isEmpty())
            throw new Exception("파일이 비어 있어요 유유");

        String fileExtension = "";
        String originalFileName = file.getOriginalFilename();

        if(originalFileName != null && originalFileName.contains(".")){
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        return UUID.randomUUID() + fileExtension;


    }



}
