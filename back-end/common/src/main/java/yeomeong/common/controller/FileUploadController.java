package yeomeong.common.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yeomeong.common.service.S3Service;


@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final S3Service s3Service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @RequestParam(value = "file") MultipartFile file){

        try {
            String fileUrl = s3Service.uploadFile(file);
            return ResponseEntity.ok("파일 업로드 성공" + fileUrl);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패");

        }

    }
}
