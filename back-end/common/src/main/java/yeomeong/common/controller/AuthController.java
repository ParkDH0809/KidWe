package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.auth.JoinRequestDto;
import yeomeong.common.dto.auth.RefreshResponseDto;
import yeomeong.common.security.jwt.JwtService;
import yeomeong.common.security.jwt.JwtUtil;
import yeomeong.common.service.MemberService;

@RestController
@Tag(name = "인증/인가 API", description = "인증/인가 관련 API")
public class AuthController {

    final MemberService memberService;
    final JwtService jwtService;

    public AuthController(MemberService memberService, JwtService jwtService) {
        this.memberService = memberService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody JoinRequestDto joinRequestDto) {
        memberService.joinMember(joinRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Access JWT 재요청", description = "Access JWT를 재요청합니다.")
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