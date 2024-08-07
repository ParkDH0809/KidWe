package yeomeong.common.dto.medication;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MedicationDetailDto {

    private String medicineName;
    private String symptom;
    private String type;
    private String capacity;
    private String numberOfDoses;
    private LocalDate medicationExecuteDate;
    private String medicationExecuteTime;
    private String storageMethod;
    private String others;
    private String medicineUrl;
    private String parentName;
    private LocalDate signDate;

}
