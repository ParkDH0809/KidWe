package yeomeong.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.member.JoinRequestDto;
import yeomeong.common.dto.member.RefreshResponseDto;
import yeomeong.common.security.jwt.JwtService;
import yeomeong.common.security.jwt.JwtUtil;
import yeomeong.common.service.MemberService;

@Slf4j
@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    JwtService jwtService;

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