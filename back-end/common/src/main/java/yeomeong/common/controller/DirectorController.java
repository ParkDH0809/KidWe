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
import yeomeong.common.dto.member.TeacherDetailInfoResponseDto;
import yeomeong.common.dto.ban.BanCreateRequestDto;
import yeomeong.common.dto.ban.BanNameChangeRequestDto;
import yeomeong.common.dto.kindergarten.KindergartenSaveRequestDto;
import yeomeong.common.dto.member.TeacherChangeBanRequestDto;
import yeomeong.common.service.ApprovalService;
import yeomeong.common.service.BanService;
import yeomeong.common.service.KindergartenService;

@RestController
@RequestMapping("/directors")
@Tag(name = "원장 권한 API", description = "원장 역할 관련 API (ROLE_DIRECTOR 권한만 접근 가능)")
public class DirectorController {

    private final BanService banService;
    private final KindergartenService kindergartenService;
    private final ApprovalService approvalService;

    public DirectorController(BanService banService, KindergartenService kindergartenService,
        ApprovalService approvalService) {
        this.banService = banService;
        this.kindergartenService = kindergartenService;
        this.approvalService = approvalService;
    }

    @Operation(summary = "유치원 생성", description = "유치원을 생성합니다.")
    @PostMapping("/kindergarten")
    public ResponseEntity<Void> createKindergarten(@RequestBody KindergartenSaveRequestDto kindergartenSaveRequestDto) {
        kindergartenService.createKindergarten(kindergartenSaveRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "반 생성", description = "반을 생성합니다.")
    @PostMapping("/ban")
    public ResponseEntity<Void> createBan(@RequestBody BanCreateRequestDto banCreateRequestDto) {
        banService.createBan(banCreateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "특정 반 이름 변경", description = "특정 반 이름을 변경합니다.")
    @PutMapping("/ban")
    public ResponseEntity<Void> changeBanName(@RequestBody BanNameChangeRequestDto banNameChangeRequestDto) {
        banService.changeBanName(banNameChangeRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "승인 대기 선생님 리스트 조회", description = "승인 대기 선생님 리스트를 조회합니다.")
    @GetMapping("/teachers/pending")
    public ResponseEntity<List<TeacherDetailInfoResponseDto>> getPendingTeachers(@RequestParam Long kindergartenId) {
            return ResponseEntity.status(HttpStatus.OK).body(approvalService.getPendingTeachers(kindergartenId));
    }

    @Operation(summary = "승인된 선생님 리스트 조회", description = "승인된 선생님 리스트를 조회합니다.")
    @GetMapping("/teachers/accept")
    public ResponseEntity<List<TeacherDetailInfoResponseDto>> getAcceptTeachers(@RequestParam Long kindergartenId) {
        return ResponseEntity.status(HttpStatus.OK).body(approvalService.getAcceptTeachers(kindergartenId));
    }

    @Operation(summary = "선생님 승인 여부", description = "대기 중인 선생님 승인 API입니다.")
    @PutMapping("/teachers/pending")
    public ResponseEntity<Void> updateTeacherApproval(@RequestBody AcceptRequestDto acceptRequestDto) {
        approvalService.acceptTeacherRequestDto(acceptRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "선생님 반 변경", description = "선생님의 담당 반을 변경합니다.")
    @PutMapping("/ban/teachers")
    public ResponseEntity<Void> updateTeachersBan(@RequestBody TeacherChangeBanRequestDto teacherChangeBanRequestDto) {
        banService.updateTeachersBan(teacherChangeBanRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
