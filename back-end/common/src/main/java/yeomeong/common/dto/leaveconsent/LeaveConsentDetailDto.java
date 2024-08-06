package yeomeong.common.dto.leaveconsent;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LeaveConsentDetailDto {

    private LocalDate leaveDate;
    private LocalDateTime leaveTime;

    private String guardianRelationship;
    private String guardianContact;

    private String emergencyRelationship;
    private String emergencyContact;

    private LocalDate signDate;
    private String signUrl;



}
