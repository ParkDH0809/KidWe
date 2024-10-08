package yeomeong.common.entity.medication;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id")
    private Kid kid;



    private String symptom;

    private String type;

    private String medicineImageUrl;

    private String capacity;

    @Column(name = "medication_execute_due_date")
    private LocalDate medicationExecuteDueDate;

    private String medicationExecuteTime;

    private String numberOfDoses;

    private String storageMethod;

    private String others;

    private String guardianName;

    private LocalDateTime medicationCreatedDateTime;

    private String signUrl;

    @ColumnDefault("false")
    private boolean isDeleted;

    public Medication(String name, Kid kid, String symptom, String type, String medicineImageUrl, String capacity, LocalDate medicationExecuteDueDate, String medicationExecuteTime, String numberOfDoses, String storageMethod, String guardianName,String others, String signUrl) {
        this.name = name;
        this.kid = kid;
        this.symptom = symptom;
        this.type = type;
        this.medicineImageUrl = medicineImageUrl;
        this.capacity = capacity;
        this.medicationExecuteDueDate = medicationExecuteDueDate;
        this.medicationExecuteTime = medicationExecuteTime;
        this.numberOfDoses = numberOfDoses;
        this.storageMethod = storageMethod;
        this.others = others;
        this.guardianName = guardianName;
        this.medicationCreatedDateTime = LocalDateTime.now();
        this.signUrl = signUrl;
    }
}
