package yeomeong.common.dto.medication;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MedicationCreateDto {

    private Long kidId;
    private LocalDateTime medicationExecuteDateTime;
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
