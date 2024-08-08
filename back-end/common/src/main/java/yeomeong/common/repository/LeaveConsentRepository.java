package yeomeong.common.repository;


import yeomeong.common.dto.leaveconsent.LeaveConsentByMonthAndBanListDto;
import yeomeong.common.dto.leaveconsent.LeaveConsentDetailDto;
import yeomeong.common.entity.LeaveConsent;


import java.util.List;

public interface LeaveConsentRepository {


    void save(LeaveConsent leaveConsent);

    //원장, 선생용
    List<LeaveConsentByMonthAndBanListDto> findAllByBan_IdAndYearAndMonth(Long banId, int year, int month);


    //학부모용
    List<LeaveConsentByMonthAndBanListDto> findAllByKid_IdAndYearAndMonth(Long kidId, int year, int month);


    void remove(LeaveConsent leaveConsent);

    LeaveConsent findById(Long leaveConsentId);

    LeaveConsentDetailDto getLeaveConsentDetail(Long memberId,Long leaveConsentId);


}
