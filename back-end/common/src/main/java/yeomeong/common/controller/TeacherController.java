package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.approval.AcceptRequestDto;
import yeomeong.common.dto.approval.PendingKidResponseDto;
import yeomeong.common.dto.approval.TeacherJoinKindergartenRequestDto;
import yeomeong.common.service.ApprovalService;

@RestController
@RequestMapping("/teachers")
@Tag(name = "선생 권한 API", description = "선생 역할 관련 API (ROLE_TEACHER 권한만 접근 가능합니다.)")
public class TeacherController {

    private final ApprovalService approvalService;

    public TeacherController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @Operation(summary = "선생님 유치원 가입 신청", description = "선생님이 유치원 가입을 신청합니다.")
    @PostMapping("/kindergarten")
    public ResponseEntity<Void> applyForKindergarten(@RequestBody TeacherJoinKindergartenRequestDto teacherJoinKindergartenRequestDto) {
        approvalService.applyForKindergartenByTeacher(teacherJoinKindergartenRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "승인 대기 원생 리스트 조회", description = "승인 대기 원생 리스트를 조회합니다.")
    @GetMapping("/pending-kids")
    public ResponseEntity<List<PendingKidResponseDto>> getPendingKids(@RequestParam Long kindergartenId) {
        return ResponseEntity.status(HttpStatus.OK).body(approvalService.getPendingKids(kindergartenId));
    }

    @Operation(summary = "원생 승인 여부", description = "대기 중인 원생 승인 API입니다.")
    @PutMapping("/pending-kids")
    public ResponseEntity<Void> updateKidApproval(@RequestBody AcceptRequestDto acceptRequestDto) {
        approvalService.acceptKidRequestDto(acceptRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
