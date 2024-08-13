package yeomeong.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;

@Component
@RequiredArgsConstructor
public class FileUtil {
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

    private static MultipartFile resizeFile(MultipartFile file, int width, int height) throws Exception {
        try {
            // MultipartFile -> BufferedImage Convert
            BufferedImage image = ImageIO.read(file.getInputStream());
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

            // origin 이미지가 resizing될 사이즈보다 작을 경우 resizing 작업 안 함
            if (originWidth < width && originHeight < height) {
                return file;
            }

            MarvinImage imageMarvin = new MarvinImage(image);

            Scale scale = new Scale();
            scale.load();

            // 주어진 width와 height로 설정
            scale.setAttribute("newWidth", width);
            scale.setAttribute("newHeight", height);

            // 비율을 무시하고 리사이즈
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, "jpg", baos); // 여기서 fileFormatName을 "jpg"로 설정
            baos.flush();

            return new MockMultipartFile(file.getOriginalFilename(), baos.toByteArray());
        } catch (IOException e) {
            throw new CustomException(ErrorCode.RESIZING_ERRORH);
        }
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

    public static String uploadThumbToS3(AmazonS3 s3Client, String bucketName, MultipartFile file, int width, int height) {
        if (file == null) {
            return null;
        }

        String fileName = null;
        try {
            fileName = FileUtil.convertFileName(resizeFile(file, width, height));

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3Client.putObject(new PutObjectRequest(bucketName + "/thumb", fileName, file.getInputStream(), metadata));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }
}