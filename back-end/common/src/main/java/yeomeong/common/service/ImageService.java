package yeomeong.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yeomeong.common.entity.post.AnnouncementImage;
import yeomeong.common.util.FileUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final AmazonS3 s3Client;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public void saveImage(MultipartFile image, String directoryName) throws Exception {
        if(image != null ){
            ObjectMetadata metadata =new ObjectMetadata();
            metadata.setContentLength(image.getSize());
            metadata.setContentType(image.getContentType());


            String fileName = FileUtil.convertFileName(image);

            try {
                s3Client.putObject(new PutObjectRequest(bucketName,fileName, image.getInputStream(),metadata));
            }
            catch (Exception e){
                e.printStackTrace();
            }

            //s3Client.getUrl(bucketName, fileName).toString(),
        }
    }

//    public void saveImages(List<MultipartFile> images){
//        if(images != null ){
//            for( MultipartFile image : images){
//
//                ObjectMetadata metadata =new ObjectMetadata();
//
//                metadata.setContentLength(image.getSize());
//                metadata.setContentType(image.getContentType());
//
//                String fileName = FileUtil.convertFileName(image);
//
//                try {
//
//                    s3Client.putObject(new PutObjectRequest(bucketName,fileName, image.getInputStream(),metadata));
//
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                }
//
//                AnnouncementImage announcementImage =new AnnouncementImage(
//                        s3Client.getUrl(bucketName, fileName).toString(),
//                        announcement
//                );
//                announcementImageRepository.save(announcementImage);
//                announcement.getAnnouncementImages().add(announcementImage);
//
//            }
//        }
//    }
}
