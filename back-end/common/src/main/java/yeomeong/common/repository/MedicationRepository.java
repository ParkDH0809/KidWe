package yeomeong.common.repository;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yeomeong.common.dto.medication.MedicationByKidAndMonthDto;
import yeomeong.common.dto.medication.MedicationByKidDto;
import yeomeong.common.dto.medication.MedicationCreateDto;
import yeomeong.common.dto.medication.MedicationDetailDto;
import yeomeong.common.entity.medication.Medication;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.util.FileUtil;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class MedicationRepository {

    private final EntityManager em;
    private final KidRepository kidRepository;
    private final AmazonS3 s3Client;
    private final MemberRepository memberRepository;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public void save(Medication medication){
        em.persist(medication);
    }

    /**
     *  반 별, 월 별 투약의뢰서 불러오기
     *  학부모랑 선생님에 따라 다르게 쿼리 구현
     */
    public List<MedicationByKidAndMonthDto> medicationByKidAndMonthDtoList(Long banId, int year, int month) {
        // 시작과 끝 날짜 계산
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);


        return em.createQuery(
                        "SELECT new yeomeong.common.dto.medication.MedicationByKidAndMonthDto (m.id, k.name, b.name, m.medicationCreatedDateTime, m.isDeleted) " +
                                "FROM Medication m " +
                                "JOIN m.kid k " +
                                "JOIN k.ban b " +
                                "WHERE m.isDeleted = false and m.medicationExecuteDueDate BETWEEN :startDate AND :endDate " +
                                "AND b.id = :banId "
                        ,MedicationByKidAndMonthDto.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("banId", banId)
                .getResultList();

    }

    public List<MedicationByKidDto> medicationByKidDtoList(Long kidId, int year, int month){
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        //아이별 투약 의뢰서 ( 학부모용 )
        return em.createQuery("select new yeomeong.common.dto.medication.MedicationByKidDto (m.id, k.name, k.ban.name,m.medicationCreatedDateTime, m.isDeleted ) " +
                " from Medication m " + "Join m.kid k " +
                "where k.id = :kidId and m.isDeleted = false and " +
                "m.medicationExecuteDueDate BETWEEN :startDate AND :endDate order by m.medicationCreatedDateTime DESC ",
                MedicationByKidDto.class)
                .setParameter("kidId",kidId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }


    //투약의뢰서 상세 보기
    public MedicationDetailDto getMedicationDetail(Long medicationId, Long memberId){
        Medication medication = em.find(Medication.class, medicationId);

        Member member = em.find(Member.class, memberId);

        return new MedicationDetailDto(
                medication.getName(),
                medication.getSymptom(),
                medication.getType(),
                medication.getCapacity(),
                medication.getNumberOfDoses(),
                medication.getMedicationExecuteDueDate(),
                medication.getMedicationExecuteTime(),
                medication.getStorageMethod(),
                medication.getOthers(),
                medication.getMedicineImageUrl(),
                medication.getGuardianName(),
                medication.getMedicationCreatedDateTime().toLocalDate());
    }

    //투약의뢰서 생성하기
    @Transactional
    public MedicationCreateDto createMedication(MultipartFile medicineImage, MultipartFile signImage, MedicationCreateDto medicationCreateDto, Long kidId, Long memberId) throws Exception {
        //아이 정보 기준으로 생성하기

        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new RuntimeException("해당 아이가 없습니다."));

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("해당 맴버가 없습니다."));

        String medicineName = FileUtil.convertFileName(medicineImage);

        ObjectMetadata objectMetadataOfMedicine = new ObjectMetadata();
        objectMetadataOfMedicine.setContentLength(medicineImage.getSize());
        objectMetadataOfMedicine.setContentType(medicineImage.getContentType());

        s3Client.putObject(new PutObjectRequest(bucketName, medicineName, medicineImage.getInputStream(), objectMetadataOfMedicine));

        String sighName = FileUtil.convertFileName(signImage);

        ObjectMetadata objectMetadataOfSign = new ObjectMetadata();
        objectMetadataOfSign.setContentType(signImage.getContentType());
        objectMetadataOfSign.setContentLength(signImage.getSize());

        s3Client.putObject(new PutObjectRequest(bucketName, sighName, signImage.getInputStream(), objectMetadataOfSign));


        String medicineUrl = s3Client.getUrl(bucketName, medicineName).toString();
        String signUrl = s3Client.getUrl(bucketName, sighName).toString();

        Medication medication =new Medication(
                medicationCreateDto.getMedicineName(),
                kid,
                medicationCreateDto.getSymptom(),
                medicationCreateDto.getType(),
                medicineUrl,
                medicationCreateDto.getCapacity(),
                medicationCreateDto.getMedicationExecuteDueDate(),
                medicationCreateDto.getMedicationExecuteTime(),
                medicationCreateDto.getNumberOfDoses(),
                medicationCreateDto.getStorageMethod(),
                member.getName(),
                medicationCreateDto.getOthers(),
                signUrl
                );

        em.persist(medication);

        return medicationCreateDto;
    }

    //투약의뢰서 삭제하기
    public void removeMedication(Long medicationId){

        Medication medication = em.find(Medication.class, medicationId);

        medication.setDeleted(true);

    }
}
