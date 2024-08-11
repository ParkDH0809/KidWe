package yeomeong.common.dto.leaveconsent;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveConsentByMonthAndBanListDto {


    private Long leaveConsentId;
    private String kidPicture;
    private String kidName;
    private String banName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 M월 d일")
    private LocalDate leaveDate;



    @QueryProjection
    public LeaveConsentByMonthAndBanListDto(Long leaveConsentId,String kidPicture ,String kidName, String banName, LocalDate leaveDate) {
        this.leaveConsentId = leaveConsentId;
        this.kidPicture = kidPicture;
        this.kidName = kidName;
        this.banName = banName;
        this.leaveDate = leaveDate;
    }

}
