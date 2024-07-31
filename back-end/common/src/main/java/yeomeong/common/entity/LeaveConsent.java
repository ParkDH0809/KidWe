package yeomeong.common.entity.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.jpa.member.Kid;

import java.time.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class LeaveConsent {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kid kid;

    private LocalDate leaveDate;
    private LocalTime localTime;

    private String leaveMethod;

    private String guardianRelationship;
    private String guardianContact;

    private String emergencyRelationship;
    private String emergencyContact;

    private String signUrl;

    public LeaveConsent() {

    }

    public LeaveConsent(Kid kid, LocalDate leaveDate, LocalTime localTime, String leaveMethod, String guardianRelationship, String guardianContact, String emergencyRelationship, String emergencyContact, String signUrl) {
        this.kid = kid;
        this.leaveDate = leaveDate;
        this.localTime = localTime;
        this.leaveMethod = leaveMethod;
        this.guardianRelationship = guardianRelationship;
        this.guardianContact = guardianContact;
        this.emergencyRelationship = emergencyRelationship;
        this.emergencyContact = emergencyContact;
        this.signUrl = signUrl;
    }
}
