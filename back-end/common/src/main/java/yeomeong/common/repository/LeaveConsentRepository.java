package yeomeong.common.repository.jpa;


import yeomeong.common.dto.leaveconsent.LeaveConsentByMonthAndBanListDto;
import yeomeong.common.entity.jpa.LeaveConsent;

import java.util.List;

public interface LeaveConsentRepository {


    public void save(LeaveConsent leaveConsent);

    //원장, 선생용
    public List<LeaveConsentByMonthAndBanListDto> findAllByBan_IdAndYearAndMonth(Long banId, int year, int month);


    //학부모용
    public List<LeaveConsentByMonthAndBanListDto> findAllByKid_IdAndYearAndMonth(Long kidId, int year, int month);


    public void remove(LeaveConsent leaveConsent);

    public LeaveConsent findById(Long leaveConsentId);
}
