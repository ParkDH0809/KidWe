package yeomeong.common.dto.leaveconsent;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class LeaveConsentDetailDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 M월 d일")
    private LocalDate leaveDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime leaveTime;


    private String leaveMethod;

    private String guardianRelationship;
    private String guardianContact;

    private String emergencyRelationship;
    private String emergencyContact;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 M월 d일")
    private LocalDate signDate;
    private String parentName;
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
