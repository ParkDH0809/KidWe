package yeomeong.common.repository.jpa;

import org.junit.jupiter.api.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.entity.jpa.member.Member;
import yeomeong.common.entity.jpa.member.rtype;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Member Repository Test")
class MemberRepositoryTest {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MemberRepositoryTest.class);

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
    @DisplayName("회원 조회 - 성공")
    void findByEmail() {
        Assertions.assertEquals(member.getEmail(), memberRepository.findByEmail(member.getEmail()).getEmail());
        log.info("[member Email]: " + member.getEmail());
    }

    @Test
    @DisplayName("회원 조회 - 실패: 회원이 없는 경우")
    void findByEmailFailure() {
        assertThrows(EmptyResultDataAccessException.class, () -> memberRepository.findByEmail("nothing@user.com"));
    }

}