package yeomeong.common.entity.jpa.medication;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.jpa.kindergarten.Kindergarten;
import yeomeong.common.entity.jpa.member.Kid;

import java.time.LocalDate;


@Entity
@Getter
@Setter
public class Medication {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kid kid;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kindergarten kinderGarten;

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

    private LocalDate medicationStartDate;
    private LocalDate medicationExecuteDate;

    private String signUrl;

}
