package yeomeong.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.service.MemberService;

@Slf4j
@RestController("/member")
public class MemberController {

    final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getMemberProfile(Authentication authentication) {
        return ResponseEntity.ok(memberService.getMemberProfile(authentication.getName()));
    }

    @DeleteMapping("/profile")
    public ResponseEntity<?> updateMemberProfile(Authentication authentication) {
        memberService.deleteMember(authentication.getName());
        return ResponseEntity.ok().build();
    }

}