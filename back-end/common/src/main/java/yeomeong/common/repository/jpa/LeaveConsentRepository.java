package yeomeong.common.repository.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.dto.leaveconsent.LeaveConsentByMonthAndBanListDto;
import yeomeong.common.entity.jpa.LeaveConsent;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveConsentRepository extends JpaRepository<LeaveConsent,Long> {

    public List<LeaveConsentByMonthAndBanListDto> findAllByKid_Ban_IdAndYearAndMonth(Long banId, int year, int month);
}
