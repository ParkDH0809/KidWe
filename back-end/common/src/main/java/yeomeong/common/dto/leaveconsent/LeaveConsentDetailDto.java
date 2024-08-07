package yeomeong.common.dto.leaveconsent;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class LeaveConsentDetailDto {

    private LocalDate leaveDate;
    private LocalTime leaveTime;

    private String guardianRelationship;
    private String guardianContact;

    private String emergencyRelationship;
    private String emergencyContact;

    private LocalDate signDate;
    private String signUrl;

    public LeaveConsentDetailDto(LocalDate leaveDate, LocalTime leaveTime, String guardianRelationship, String guardianContact, String emergencyRelationship, String emergencyContact, LocalDate signDate, String signUrl) {
        this.leaveDate = leaveDate;
        this.leaveTime = leaveTime;
        this.guardianRelationship = guardianRelationship;
        this.guardianContact = guardianContact;
        this.emergencyRelationship = emergencyRelationship;
        this.emergencyContact = emergencyContact;
        this.signDate = signDate;
        this.signUrl = signUrl;
    }
}
