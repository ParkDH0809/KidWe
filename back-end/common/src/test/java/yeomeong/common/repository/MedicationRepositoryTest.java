package yeomeong.common.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.medication.MedicationByKidAndMonthDto;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.medication.Medication;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class MedicationRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private KindergartenRepository kindergartenRepository;
    @Autowired
    private BanRepository banRepository;

    @Test
    public void medication() throws Exception {

        //given
        Kindergarten kinderGarten = new Kindergarten();
        kinderGarten.setName("지환이의 유치원");
        kindergartenRepository.save(kinderGarten);
        Member member =new Member();
        Ban ban =new Ban();
        List<Kid> kidMembers =new ArrayList<>();
        Kid kid =new Kid();

        kidMembers.add(kid);
        banRepository.save(ban);
        ban.setKindergarten(kinderGarten);
        member.setBan(ban);
        member.setName("변지환");
//        member.setKidMember(kidMembers);

        memberRepository.save(member);
        Medication medication =new Medication();
        medication.setMedicationExecuteDate(LocalDate.parse("2024-06-06"));

        medicationRepository.save(medication);
        //when
        List<MedicationByKidAndMonthDto> medicationByKidAndMonthDtos = medicationRepository.medicationByKidAndMonthDtoList(member.getId(),
                medication.getMedicationExecuteDate().getYear(),
                medication.getMedicationExecuteDate().getMonthValue());

        //then
        System.out.println("********************");
        System.out.println(medicationByKidAndMonthDtos);

    }
}