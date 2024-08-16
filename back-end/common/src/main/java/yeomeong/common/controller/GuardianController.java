package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import yeomeong.common.dto.approval.KidJoinKindergartenRequestDto;
import yeomeong.common.service.ApprovalService;

@RestController
@RequestMapping("/guardians")
@Tag(name = "부모 권한 API", description = "부모 역할 관련 API (ROLE_GUARDIAN 권한만 접근 가능)")
public class GuardianController {

    private final ApprovalService approvalService;

    public GuardianController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @Operation(summary = "아이 정보 생성 & 유치원 가입 신청", description = "부모님이 아이 정보를 생성해 유치원 가입을 신청합니다.")
    @PostMapping("/kindergarten")
    public ResponseEntity<Void> applyForKindergarten(
            @RequestPart("dto") KidJoinKindergartenRequestDto kidJoinKindergartenRequestDto,
            @RequestPart(required = false) MultipartFile picture) {
        approvalService.applyForKindergartenByGuardian(kidJoinKindergartenRequestDto, picture);
        return ResponseEntity.ok().build();
    }

}
