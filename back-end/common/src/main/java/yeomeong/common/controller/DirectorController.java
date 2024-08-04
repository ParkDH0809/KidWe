package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.kindergarten.KindergartenApprovalStatusDto;
import yeomeong.common.service.MemberService;

@RestController
@RequestMapping("/directors")
@Tag(name = "원장 API", description = "원장 권한 API")
public class DirectorController {

    private final MemberService memberService;

    public DirectorController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "선생 가입 조회", description = "가입 신청한 선생님 승인 여부에 대한 API입니다.")
    @PutMapping
    public ResponseEntity<Void> getBanInfo(@RequestBody KindergartenApprovalStatusDto kindergartenApprovalStatusDto) {
        memberService.updateMemberState(kindergartenApprovalStatusDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
