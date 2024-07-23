package yeomeong.common.dto.medication;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicationCreateDto {

    private Long kidId;
    private LocalDate medicationExecuteDate;
    private String symptom;
    private String medicineUrl;
    private String medicineName;
    private String type;
    private String capacity;
    private String numberOfDoses;
    private String medicationExecuteTime;
    private String storageMethod;
    private String others;
    private String signUrl;

}
