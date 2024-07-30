package yeomeong.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.service.MemberService;

@Slf4j
@RestController("/member")
public class MemberController {

    final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/profile")
    public ResponseEntity<MemberProfileResponseDto> getMemberProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("member: {}", userDetails.getUsername());
        return ResponseEntity.ok(memberService.getMemberProfile(userDetails.getUsername()));
    }

}