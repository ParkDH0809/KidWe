package yeomeong.common.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yeomeong.common.dto.medication.MedicationByKidAndMonthDto;
import yeomeong.common.dto.medication.MedicationByKidDto;
import yeomeong.common.dto.medication.MedicationCreateDto;
import yeomeong.common.dto.medication.MedicationDetailDto;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.medication.Medication;

import java.util.List;

@Repository
@RequiredArgsConstructor

public class MedicationRepository {

    private final EntityManager em;
    private final BanRepository banRepository;

    public void save(Medication medication){
        em.persist(medication);
    }

    /**
     *  반 별, 월 별 투약의뢰서 불러오기
     *  학부모랑 선생님에 따라 다르게 쿼리 구현
     */
    public List<MedicationByKidAndMonthDto> medicationByKidAndMonthDtoList(Long banId, int year, int month){

        //한 유치원의 모든 반에 대한 투약의뢰서
        return em.createQuery("select m.id," +
                                " m.kid.name, " +
                                " m.kid.ban.name " +
                "from Medication m where YEAR(m.medicationExecuteDate) = :year and " +
                                "MONTH(m.medicationExecuteDate) = :month and"+
                                " m.ban.id = :banId",
                MedicationByKidAndMonthDto.class)
                .setParameter("year", year)
                .setParameter("month", month)
                .setParameter("banId", banId)
                .getResultList();
    }

    public List<MedicationByKidDto> medicationByKidDtoList(Long kidId, int year, int month){

        //아이별 투약 의뢰서 ( 학부모용 )
        return em.createQuery("select m.id, " +
                                "m.kid.name" +
                " from Medication m " +
                "where m.kid.id = :kidId and " +
                "YEAR(m.medicationExecuteDate) = :year and " +
                "MONTH(m.medicationExecuteDate) = :month",
                MedicationByKidDto.class)
                .setParameter("kidId",kidId)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();
    }


    //투약의뢰서 상세 보기
    public MedicationDetailDto getMedicationDetail(Long medicationId){
        Medication medication = em.find(Medication.class, medicationId);

        return new MedicationDetailDto(
                medication.getName(),
                medication.getSymptom(),
                medication.getType(),
                medication.getCapacity(),
                medication.getNumberOfDoses(),
                medication.getMedicationExecuteTime(),
                medication.getStorageMethod(),
                medication.getOthers(),
                medication.getMedicineUrl() );
    }

    //투약의뢰서 생성하기
    public void createMedication(MedicationCreateDto medicationCreateDto, Long kidId){
        //아이 정보 기준으로 생성하기

        Medication medication =new Medication(
              kidId,
                medicationCreateDto.getMedicationExecuteDate(),
                medicationCreateDto.getSymptom(),
                medicationCreateDto.getMedicineUrl(),
                medicationCreateDto.getType(),
                medicationCreateDto.getCapacity(),
                medicationCreateDto.getNumberOfDoses(),
                medicationCreateDto.getMedicationExecuteTime(),
                medicationCreateDto.getStorageMethod(),
                medicationCreateDto.getOthers(),
                medicationCreateDto.getSignUrl()
                );

        em.persist(medication);
    }

    //투약의뢰서 삭제하기
    public void removeMedication(Long medicationId){

        Medication medication = em.find(Medication.class, medicationId);

        em.remove(medication);
    }
}
