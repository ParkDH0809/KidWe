package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.auth.LoginRequestDto;
import yeomeong.common.dto.auth.RefreshResponseDto;
import yeomeong.common.dto.member.MemberSaveRequestDto;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
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
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody MemberSaveRequestDto memberSaveRequestDto) {
        memberService.joinMember(memberSaveRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "회원 로그인", description = "인증을 요청합니다.")
    @PostMapping("/login")
    public ResponseEntity<Void> fakeLogin(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "회원 로그아웃", description = "로그아웃을 합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> fakeLogout(@RequestHeader("Authorization") String accessToken) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Access JWT 재요청", description = "Access JWT를 재요청합니다.")
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String refreshToken) {
        if (jwtService.isTokenStored(refreshToken)) {
            return ResponseEntity.ok(
                RefreshResponseDto
                    .builder()
                    .accessToken(JwtUtil.createAccessToken(memberService.getMemberByEmail(JwtUtil.getLoginEmail(refreshToken))))
                    .build()
            );
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomException(ErrorCode.UNAUTHENTICATED_EXPIRED_REFRESH_TOKEN));
    }

}