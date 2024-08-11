package yeomeong.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUtil {
    private static AmazonS3 s3Client;

    @Value("${aws.s3.bucket-name}")
    private static String bucketName;

    public FileUtil(AmazonS3 s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    private static String convertFileName(MultipartFile file) throws Exception {
        if(file.isEmpty())
            throw new Exception("파일이 비어 있어요 유유");

        String fileExtension = "";
        String originalFileName = file.getOriginalFilename();

        if(originalFileName != null && originalFileName.contains(".")){
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        return UUID.randomUUID() + fileExtension;

    }

    public static String uploadFileToS3(MultipartFile file) {
        String fileName = null;
        try {
            fileName = FileUtil.convertFileName(file);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    public static String uploadFileToS3(AmazonS3 s3Client, String bucketName, MultipartFile file) {
        if (file == null) {
            return null;
        }

        String fileName = null;
        try {
            fileName = FileUtil.convertFileName(file);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

}
