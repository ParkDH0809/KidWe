package yeomeong.common.service;



import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

  private AmazonS3Client s3Client;

  private final String bucketName = "common-kidwe";


    public String uploadFile(MultipartFile file, String domain) throws IOException {

        String fileName = UUID.randomUUID() + domain + "/" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));


        return s3Client.getUrl(bucketName, fileName).toString();
    }
}
