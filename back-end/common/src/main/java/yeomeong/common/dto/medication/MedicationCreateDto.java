package yeomeong.common.dto.medication;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MedicationCreateDto {

    private Long kidId;
    private String medicineName;
    private String symptom;
    private String type;
    private String medicineUrl;
    private String capacity;
    private String medicationExecuteTime;

    private String numberOfDoses;

    private String storageMethod;
    private String others;

    private LocalDate medicationExecuteDueDate;

    private LocalDateTime medicationCreatedDateTime;


    private String signUrl;

}
