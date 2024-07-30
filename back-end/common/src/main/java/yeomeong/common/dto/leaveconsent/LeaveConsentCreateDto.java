package yeomeong.common.dto.leaveconsent;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class LeaveConsentCreateDto {

    private LocalDate leaveDate;
    private LocalTime leaveTime;

    private String leaveMethod;

    private String guardianRelationship;
    private String guardianContact;

    private String emergencyRelationship;
    private String emergencyContact;

    private String signUrl;

}
