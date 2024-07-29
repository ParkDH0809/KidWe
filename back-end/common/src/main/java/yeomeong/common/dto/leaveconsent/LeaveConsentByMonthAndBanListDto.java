package yeomeong.common.dto.leaveconsent;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveConsentByMonthAndBanListDto {


    private Long leaveConsentId;
    private String kidName;
    private String banName;
    private LocalDate LeaveDate;

}
