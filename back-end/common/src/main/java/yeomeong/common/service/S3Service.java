package yeomeong.common.service;



import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;



    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("업로드할 파일이 비어 있습니다.");
        }

        String fileName = String.valueOf(UUID.randomUUID());
        File pictureFile = convertMultiPartFileToFile(file);

//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(file.getSize());
//        metadata.setContentType(file.getContentType());


        try {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, pictureFile));
        } catch (Exception e) {
            throw new IOException("S3에 파일 업로드 중 오류 발생: " + e.getMessage(), e);
        }

        return s3Client.getUrl(bucketName, fileName).toString();
    }


    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        // 원본 파일 이름을 가져오고, null 체크를 합니다.
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IOException("파일 이름이 유효하지 않습니다.");
        }

        // 시스템의 임시 디렉토리에 파일을 저장
        File convertedFile = new File(System.getProperty("java.io.tmpdir") + File.separator + originalFilename);

        try (OutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new IOException("파일 변환 중 오류 발생: " + e.getMessage(), e);
        }

        return convertedFile;
    }
}
