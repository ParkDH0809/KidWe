package yeomeong.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;

@Component
public class FileUtil {

    private static String convertFileName(MultipartFile file) throws Exception {
        if(file.isEmpty()) throw new Exception("파일이 비어 있습니다");

        String fileExtension = "";
        String originalFileName = file.getOriginalFilename();

        if(originalFileName != null && originalFileName.contains(".")){
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        return UUID.randomUUID() + fileExtension;
    }

    public static String uploadFileToS3( AmazonS3 s3Client,String bucketName, MultipartFile file) {
        if (file == null) {
            return null;
        }

        String fileName = null;
        try {
            fileName = FileUtil.convertFileName(file);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3Client.putObject(new PutObjectRequest(bucketName,
                     fileName, file.getInputStream(), metadata));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }

    public static String uploadOriginalAndThumbnailToS3(AmazonS3 s3Client, String bucketName, MultipartFile file) throws Exception {
        if(file == null) return null;

        return uploadFileToS3(s3Client, bucketName, file);
    }

    // uploadFileToS3 메소드 오버로딩 (파일 이름 추가)
    public static String uploadFileToS3(AmazonS3 s3Client, String bucketName, MultipartFile file, String fileName) {
        if (file == null) {
            return null;
        }
        try {
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