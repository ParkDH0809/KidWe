package yeomeong.common.dto.medication;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MedicationDetailDto {

    private String medicineName;
    private String symptom;
    private String type;
    private String capacity;
    private String numberOfDoses;
    private String medicationExecuteTime;
    private String storageMethod;
    private String others;
    private String medicineUrl;

}
