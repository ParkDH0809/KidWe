package yeomeong.common.dto.leaveconsent;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class LeaveConsentByMonthAndBanListDto {


    private Long leaveConsentId;
    private String kidName;
    private String banName;
    private LocalDate leaveDate;
    private LocalTime leaveTime;


    @QueryProjection
    public LeaveConsentByMonthAndBanListDto(Long leaveConsentId, String kidName, String banName, LocalDate leaveDate, LocalTime leaveTime) {
        this.leaveConsentId = leaveConsentId;
        this.kidName = kidName;
        this.banName = banName;
        this.leaveDate = leaveDate;
        this.leaveTime = leaveTime;
    }

}
