package yeomeong.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.member.JoinRequestDto;
import yeomeong.common.dto.member.RefreshResponseDto;
import yeomeong.common.security.jwt.JwtService;
import yeomeong.common.security.jwt.JwtUtil;
import yeomeong.common.service.MemberService;

@RestController
public class AuthController {

    final MemberService memberService;
    final JwtService jwtService;

    public AuthController(MemberService memberService, JwtService jwtService) {
        this.memberService = memberService;
        this.jwtService = jwtService;
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody JoinRequestDto joinRequestDto) {
        memberService.joinMember(joinRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String refreshToken) {
        if (jwtService.isTokenStored(refreshToken)) {
            return ResponseEntity.ok(
                RefreshResponseDto
                    .builder()
                    .accessToken(JwtUtil.createAccessToken(JwtUtil.getLoginEmail(refreshToken)))
                    .build()
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
