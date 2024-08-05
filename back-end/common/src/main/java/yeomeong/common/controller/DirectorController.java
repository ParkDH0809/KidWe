package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.kindergarten.KindergartenApprovalStatusDto;
import yeomeong.common.dto.member.TeacherChangeBanRequestDto;
import yeomeong.common.dto.member.TeacherDetailInfoDto;
import yeomeong.common.entity.member.atype;
import yeomeong.common.service.MemberService;

@RestController
@RequestMapping("/directors")
@Tag(name = "원장 API", description = "원장 권한 API")
public class DirectorController {

    private final MemberService memberService;

    public DirectorController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "선생님 조회",
        description = "유치원에 연관된 선생님 리스트를 조회합니다. (ACCEPT: 승인된 선생님, PENDING: 승인 대기 선생님)")
    @GetMapping("{kindergartenId}/teachers/{status}")
    public ResponseEntity<List<TeacherDetailInfoDto>> getTeachersByStatus(@PathVariable Long kindergartenId, @PathVariable atype status) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getTeachersByStatus(kindergartenId, status));
    }

    @Operation(summary = "선생님 가입 승인 결정", description = "가입 신청한 선생님 승인 여부에 대한 API입니다. (ACCEPT: 승인, DECLINE: 거절)")
    @PutMapping
    public ResponseEntity<Void> updateMemberStatus(@RequestBody KindergartenApprovalStatusDto kindergartenApprovalStatusDto) {
        memberService.updateMemberState(kindergartenApprovalStatusDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "선생님 반 변경", description = "유치원 별 선생님의 반을 변경합니다.")
    @PutMapping("/ban")
    public ResponseEntity<Void> changeTeachersBan(@RequestBody TeacherChangeBanRequestDto teacherChangeBanRequestDto) {
        memberService.changeTeachersBan(teacherChangeBanRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
