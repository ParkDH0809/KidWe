package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.service.MemberService;

@Slf4j
@RestController("/members")
@Tag(name = "사용자 API", description = "사용자 관련 API")
public class MemberController {

    final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "사용자 조회", description = "특정 사용자 정보를 조회합니다.")
    @GetMapping("/profile")
    public ResponseEntity<MemberProfileResponseDto> getMemberProfile(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMemberProfile(authentication.getName()));
    }

    @Operation(summary = "사용자 삭제", description = "특정 사용자를 삭제합니다.")
    @DeleteMapping("/profile")
    public ResponseEntity<Void> updateMemberProfile(Authentication authentication) {
        memberService.deleteMember(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}