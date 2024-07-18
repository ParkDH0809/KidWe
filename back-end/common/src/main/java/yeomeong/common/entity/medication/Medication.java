package yeomeong.common.entity.medication;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.member.Kid;

import java.util.Date;

@Entity
@Getter
@Setter
public class Medication {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kid kid;

    private String symptom;

    @Enumerated(EnumType.STRING)
    private dtype dtype; // PILL, POWDER, LIQUID

    private String medicineUrl;

    private double capacity;

    @Enumerated(EnumType.STRING)
    private utype utype;

    private String medicationExecuteTime;

    private String storageMethod;

    private String others;

    private Date medicationStartDate;
    private Date medicationExecuteDate;

    private String signUrl;

}
