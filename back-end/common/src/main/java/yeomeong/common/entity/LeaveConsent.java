package yeomeong.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.member.Kid;


import java.time.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class LeaveConsent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kid kid;

    private LocalDate leaveDate;
    private LocalTime leaveTime;

    private String leaveMethod;

    private String guardianRelationship;
    private String guardianContact;

    private String emergencyRelationship;
    private String emergencyContact;

    private String signUrl;
    private LocalDate createdDate;

    private String guardianName;

    @Builder.Default
    private boolean isDeleted = false;

    public LeaveConsent() {

    }

    public LeaveConsent(Kid kid, LocalDate leaveDate, LocalTime leaveTime, String leaveMethod, String guardianRelationship, String guardianContact, String emergencyRelationship, String emergencyContact, String signUrl, LocalDate createdDate, String guardianName) {
        this.kid = kid;
        this.leaveDate = leaveDate;
        this.leaveTime = leaveTime;
        this.leaveMethod = leaveMethod;
        this.guardianRelationship = guardianRelationship;
        this.guardianContact = guardianContact;
        this.emergencyRelationship = emergencyRelationship;
        this.emergencyContact = emergencyContact;
        this.signUrl = signUrl;
        this.createdDate =  createdDate;
        this.guardianName = guardianName;
    }
}
