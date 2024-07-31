package yeomeong.common.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.auth.JoinRequestDto;
import yeomeong.common.exception.CustomException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("Member Service Test")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    JoinRequestDto joinRequestDto;

    @BeforeEach
    void setUpBeforeClass() {
        joinRequestDto = new JoinRequestDto();
        joinRequestDto.setName("이용자");
        joinRequestDto.setPassword("123456");
        joinRequestDto.setEmail("user@user.com");
        joinRequestDto.setRole("ROLE_GUARDIAN");
        joinRequestDto.setTel("010-1234-5678");
    }

    @Test
    @DisplayName("회원 가입 - 성공")
    void joinMember() {
        assertThatCode(() -> memberService.joinMember(joinRequestDto))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName(("회원 가입 - 실패 중복된 이메일이 있는 경우"))
    void joinMemberFailure() {
        memberService.joinMember(joinRequestDto);
        assertThrows(CustomException.class, () -> memberService.joinMember(joinRequestDto));
    }

}