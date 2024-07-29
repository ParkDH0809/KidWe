package yeomeong.common.entity.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.jpa.member.Kid;

import java.time.*;

@Entity
@Getter
@Setter
public class LeaveConsent {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kid kid;

    private int year;
    private int month;
    private int day;

    private String leaveMethod;

    private String guardian;
    private String guardianContact;

    private String emergencyRelationship;
    private String emergencyContact;

    private String sign;
}
