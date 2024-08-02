package yeomeong.common.entity.medication;


import jakarta.persistence.*;
import lombok.*;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Kid;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Medication {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id")
    private Kid kid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ban_id")
    private Ban ban;

    private String symptom;

    private String type;

    private String medicineUrl;

    private String capacity;
    @Column(name = "medication_execute_due_date")
    private LocalDate medicationExecuteDueDate;
    private String medicationExecuteTime;

    private String numberOfDoses;

    private String storageMethod;

    private String others;

    private LocalDateTime medicationCreatedDateTime;

    private String signUrl;

    public Medication(String name, Kid kid, String symptom, String type, String medicineUrl, String capacity, LocalDate medicationExecuteDueDate, String medicationExecuteTime, String numberOfDoses, String storageMethod, String others, LocalDateTime medicationCreatedDateTime, String signUrl) {
        this.name = name;
        this.kid = kid;
        this.symptom = symptom;
        this.type = type;
        this.medicineUrl = medicineUrl;
        this.capacity = capacity;
        this.medicationExecuteDueDate = medicationExecuteDueDate;
        this.medicationExecuteTime = medicationExecuteTime;
        this.numberOfDoses = numberOfDoses;
        this.storageMethod = storageMethod;
        this.others = others;
        this.medicationCreatedDateTime = medicationCreatedDateTime;
        this.signUrl = signUrl;
    }
}
