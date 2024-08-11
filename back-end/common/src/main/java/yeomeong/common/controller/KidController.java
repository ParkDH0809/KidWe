package yeomeong.common.controller;

import com.amazonaws.services.s3.AmazonS3;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.kid.KidDetailInfoResponseDto;
import yeomeong.common.dto.kid.KidUpdateInfoRequestDto;
import yeomeong.common.service.KidService;
import yeomeong.common.util.FileUtil;

@RestController
@RequestMapping("/kids")
@Tag(name = "아이 API", description = "아이 관련 API")
public class KidController {

    private final KidService kidService;
    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public KidController(KidService kidService, AmazonS3 s3Client) {
        this.kidService = kidService;
        this.s3Client = s3Client;
    }

    @Operation(summary = "아이 정보 조회 API", description = "특정 아이 정보를 조회합니다.")
    @GetMapping("/{kidId}")
    public ResponseEntity<KidDetailInfoResponseDto> getKid(@PathVariable Long kidId) {
        return ResponseEntity.ok().body(kidService.getKidInfo(kidId));
    }

    @Operation(summary = "아이 정보 변경 API", description = "특정 아이 정보를 변경합니다.")
    @PatchMapping
    public ResponseEntity<Void> updateKid(@RequestBody KidUpdateInfoRequestDto kidUpdateInfoRequestDto) {
        String picture = FileUtil.uploadFileToS3(s3Client, bucketName, kidUpdateInfoRequestDto.getPicture());
        kidService.updateKidInfo(kidUpdateInfoRequestDto, picture);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "아이 정보 삭제 API", description = "특정 아이 정보를 삭제합니다.")
    @DeleteMapping("/{kidId}")
    public ResponseEntity<Void> deleteKid(@PathVariable Long kidId) {
        kidService.deleteKidInfo(kidId);
        return ResponseEntity.ok().build();
    }

}
