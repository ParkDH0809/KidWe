package yeomeong.common.repository.jpa;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yeomeong.common.dto.medication.MedicationByKidAndMonthDto;
import yeomeong.common.entity.jpa.medication.Medication;
import yeomeong.common.entity.jpa.member.Member;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MedicationRepository {

    private final EntityManager em;
    private final MemberRepository memberRepository;

    public void save(Medication medication){
        em.persist(medication);
    }

    //반 별, 월 별 투약의뢰서 불러오기
    public List<MedicationByKidAndMonthDto> medicationByKidAndMonthDtoList(Long memberId, int year, int month){
        Member member = memberRepository.findOne(memberId);

        return em.createQuery("select m.kid.name, " +
                                " m.kid.ban.name " +
                "from Medication m where YEAR(m.medicationExecuteDate) = :year and " +
                                "MONTH(m.medicationExecuteDate) = :month and"+
                                " m.kinderGarten = :kindergarten",
                MedicationByKidAndMonthDto.class)
                .setParameter("year", year)
                .setParameter("month", month)
                .setParameter("kindergarten", member.getBan().getKindergarten())
                .getResultList();
    }
}
