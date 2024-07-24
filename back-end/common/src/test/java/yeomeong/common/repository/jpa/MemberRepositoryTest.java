package yeomeong.common.repository.jpa;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.entity.jpa.member.Member;
import yeomeong.common.entity.jpa.member.rtype;

@SpringBootTest
@Transactional
@DisplayName("Member Repository Test")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    Member member;

    @BeforeEach
    void setUpBeforeClass() {
        member = new Member();
        member.setName("이용자");
        member.setPassword("123456");
        member.setEmail("user@user.com");
        member.setRole(rtype.ROLE_GUARDIAN);
        member.setTel("010-1234-5678");

        memberRepository.save(member);
    }

    @Test
    @DisplayName("회원 가입 - 성공")
    void findByEmail() {
        Assertions.assertEquals(member.getEmail(), memberRepository.findByEmail(member.getEmail()).getEmail());
    }

    @Test
    @DisplayName("회원 가입 - 실패 이메일이 중복되는 경우")
    void existsByEmail() {
        Assertions.assertTrue(memberRepository.existsByEmail(member.getEmail()));
    }
}