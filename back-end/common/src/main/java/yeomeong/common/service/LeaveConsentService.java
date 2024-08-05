package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.leaveconsent.LeaveConsentByMonthAndBanListDto;
import yeomeong.common.dto.leaveconsent.LeaveConsentCreateDto;
import yeomeong.common.entity.LeaveConsent;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.LeaveConsentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveConsentService {

    private final LeaveConsentRepository leaveConsentRepository;
    private final KidRepository kidRepository;

    //반 별로 리스트 (학부모, 선생님일 때 나누어 구현)
    public List<LeaveConsentByMonthAndBanListDto> getLeaveConsentByMonthAndBanList(Long banId, int year, int month) {


        return new ArrayList<>(leaveConsentRepository.findAllByBan_IdAndYearAndMonth(banId, year, month));
    }


    public List<LeaveConsentByMonthAndBanListDto> getLeaveConsentByKid(Long kidId, int year, int month) {

        return new ArrayList<>(leaveConsentRepository.findAllByKid_IdAndYearAndMonth(kidId, year, month));
    }

    public void createLeaveConsent(Long kidId, LeaveConsentCreateDto leaveConsentCreateDto) {

        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new RuntimeException("해당하는 아이가 없어요 ㅠ_ㅠ"));

        String signUrl = new String(" ");

        LeaveConsent leaveConsent = new LeaveConsent(
                kid,
                leaveConsentCreateDto.getLeaveDate(),
                leaveConsentCreateDto.getLeaveTime(),
                leaveConsentCreateDto.getLeaveMethod(),
                leaveConsentCreateDto.getGuardianRelationship(),
                leaveConsentCreateDto.getGuardianContact(),
                leaveConsentCreateDto.getEmergencyRelationship(),
                leaveConsentCreateDto.getEmergencyContact(),
                signUrl);


        leaveConsentRepository.save(leaveConsent);
    }

    public void removeLeaveConsent(Long leaveConsentId){

        LeaveConsent leaveConsent = leaveConsentRepository.findById(leaveConsentId);

        leaveConsentRepository.remove(leaveConsent);
    }
}
