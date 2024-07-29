package yeomeong.common.entity.medication;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Kid;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Medication {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kid kid;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;

    private String symptom;

    private String type;

    private String medicineUrl;

    private String capacity;

    private String medicationExecuteTime;

    private String numberOfDoses;

    private String storageMethod;

    private String others;

    private LocalDate medicationExecuteDate;

    private String signUrl;

    public Medication(Long id, LocalDate medicationExecuteDate, String symptom, String medicineUrl, String type, String capacity, String numberOfDoses, String medicationExecuteTime, String storageMethod, String others, String signUrl){
        this.medicationExecuteDate = medicationExecuteDate;
        this.symptom = symptom;
        this.medicineUrl = medicineUrl;
        this.type = type;
        this.capacity = capacity;
        this.numberOfDoses = numberOfDoses;
        this.medicationExecuteTime = medicationExecuteTime;
        this.storageMethod = storageMethod;
        this.others = others;
        this.signUrl = signUrl;
    }
}
