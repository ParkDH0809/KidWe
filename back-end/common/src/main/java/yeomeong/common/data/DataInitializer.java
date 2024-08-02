package yeomeong.common.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.atype;
import yeomeong.common.entity.member.gtype;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.KindergartenRepository;
import yeomeong.common.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

        @Autowired
        private BanRepository banRepository; // Ban에 대한 Repository
        @Autowired
        private KindergartenRepository kindergartenRepository; // Kindergarten에 대한 Repository
        @Autowired
        private KidRepository kidRepository; // Kid에 대한 Repository
        @Autowired
        private MemberRepository memberRepository;

        @Override
        public void run(String... args) throws Exception {
            // 유치원 생성
                // 유치원 생성
                Kindergarten kindergarten = Kindergarten.builder()
                        .name("Happy Kindergarten")
                        .build();

                kindergartenRepository.save(kindergarten);

                // 반 생성
                Ban ban1 = Ban.builder()
                        .name("반 1")
                        .kindergarten(kindergarten)
                        .build();

                Ban ban2 = Ban.builder()
                        .name("반 2")
                        .kindergarten(kindergarten)
                        .build();

                banRepository.save(ban1);
                banRepository.save(ban2);

                // 아동 생성
                List<Kid> kids = new ArrayList<>();

                for (int i = 1; i <= 5; i++) {
                    Kid kid = Kid.builder()
                            .name("아이 " + i)
                            .birthday(new Date()) // 실제 생일 데이터로 변경 가능
                            .startAttendanceDate(new Date()) // 실제 출석 시작일 데이터로 변경 가능
                            .picture("picture" + i + ".jpg") // 실제 사진 경로로 변경 가능
                            .allergies("알레르기 " + i) // 실제 알레르기 정보로 변경 가능
                            .gender(i % 2 == 0 ? gtype.FEMALE : gtype.MALE) // 성별 설정
                            .tall(100 + i) // 키 데이터
                            .weight(20 + i) // 몸무게 데이터
                            .isTake(false) // 기본값 설정
                            .kindergarten(kindergarten)
                            .ban(ban1) // 첫 번째 반에 배치
                            .build();

                    kids.add(kid);
                    kidRepository.save(kid);
                }

                // 두 번째 반에 3명의 아동 추가
                for (int i = 6; i <= 8; i++) {
                    Kid kid = Kid.builder()
                            .name("아이 " + i)
                            .birthday(new Date())
                            .startAttendanceDate(new Date())
                            .picture("picture" + i + ".jpg")
                            .allergies("알레르기 " + i)
                            .gender(i % 2 == 0 ? gtype.FEMALE : gtype.MALE)
                            .tall(100 + i)
                            .weight(20 + i)
                            .isTake(false)
                            .kindergarten(kindergarten)
                            .ban(ban2) // 두 번째 반에 배치
                            .build();

                    kids.add(kid);
                    kidRepository.save(kid);
                }

                // 회원 생성
                List<Member> members = new ArrayList<>();

                for (int i = 1; i <= 5; i++) {
                    Member member = Member.builder()
                            .email("member" + i + "@example.com")
                            .password("password" + i) // 실제 비밀번호는 해시 처리해야 함
                            .name("회원 " + i)
                            .tel("010-1234-567" + i) // 전화번호 예시
                            .role(i % 3 == 0 ? rtype.ROLE_DIRECTOR : (i % 3 == 1 ? rtype.ROLE_TEACHER : rtype.ROLE_GUARDIAN))
                            .memberStatus(atype.ACCEPT) // 기본값 설정
                            .ban(ban1) // 첫 번째 반에 연결
                            .kindergarten(kindergarten) // 유치원 연결
                            .isDeleted(false)
                            .build();

                    members.add(member);
                    memberRepository.save(member);
                }
            }
}



