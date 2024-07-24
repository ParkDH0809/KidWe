package yeomeong.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.member.JoinRequestDto;
import yeomeong.common.service.MemberService;

@Slf4j
@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinRequestDto joinRequestDto) {
        memberService.joinMember(joinRequestDto);
        return ResponseEntity.ok("회원가입 완료");
    }

}