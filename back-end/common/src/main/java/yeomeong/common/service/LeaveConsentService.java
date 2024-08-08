package yeomeong.common.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yeomeong.common.dto.leaveconsent.LeaveConsentByMonthAndBanListDto;
import yeomeong.common.dto.leaveconsent.LeaveConsentCreateDto;
import yeomeong.common.dto.leaveconsent.LeaveConsentDetailDto;
import yeomeong.common.entity.LeaveConsent;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.LeaveConsentRepository;
import yeomeong.common.repository.MemberRepository;
import yeomeong.common.util.FileUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveConsentService {

    private final LeaveConsentRepository leaveConsentRepository;
    private final KidRepository kidRepository;
    private final AmazonS3 s3Client;
    private final MemberRepository memberRepository;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    //반 별로 리스트 (학부모, 선생님일 때 나누어 구현)
    public List<LeaveConsentByMonthAndBanListDto> getLeaveConsentByMonthAndBanList(Long banId, int year, int month) {


        return new ArrayList<>(leaveConsentRepository.findAllByBan_IdAndYearAndMonth(banId, year, month));
    }


    public List<LeaveConsentByMonthAndBanListDto> getLeaveConsentByKid(Long kidId, int year, int month) {

        return new ArrayList<>(leaveConsentRepository.findAllByKid_IdAndYearAndMonth(kidId, year, month));
    }

    @Transactional
    public void createLeaveConsent(Long kidId, Long memberId,LeaveConsentCreateDto leaveConsentCreateDto, MultipartFile file) throws Exception {

        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new RuntimeException("해당하는 아이가 없어요 ㅠ_ㅠ"));

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("해당하는 맴버가 없어요"));

        String fileName = FileUtil.convertFileName(file);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            s3Client.putObject(new PutObjectRequest(bucketName,fileName, file.getInputStream(), metadata));
        } catch (Exception e){
            throw new Exception("S3 파일 업로드 중 오류 발생");
        }

        String signUrl = s3Client.getUrl(bucketName,fileName).toString();

        LeaveConsent leaveConsent = new LeaveConsent(
                kid,
                leaveConsentCreateDto.getLeaveDate(),
                leaveConsentCreateDto.getLeaveTime(),
                leaveConsentCreateDto.getLeaveMethod(),
                leaveConsentCreateDto.getGuardianRelationship(),
                leaveConsentCreateDto.getGuardianContact(),
                leaveConsentCreateDto.getEmergencyRelationship(),
                leaveConsentCreateDto.getEmergencyContact(),
                signUrl,
                LocalDate.now(),
                member.getName()
        );

        leaveConsentRepository.save(leaveConsent);
    }

    public void removeLeaveConsent(Long leaveConsentId){

        LeaveConsent leaveConsent = leaveConsentRepository.findById(leaveConsentId);

        leaveConsent.setDeleted(true);
    }

    public LeaveConsentDetailDto getLeaveConsentDetail(Long memberId,Long leaveConsentId){

        return leaveConsentRepository.getLeaveConsentDetail(memberId,leaveConsentId);
    }
}
