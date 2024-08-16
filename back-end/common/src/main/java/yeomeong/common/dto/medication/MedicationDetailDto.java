package yeomeong.common.dto.medication;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MedicationDetailDto {

    private String kidPicture;
    private String medicineName;
    private String symptom;
    private String type;
    private String capacity;
    private String numberOfDoses;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 M월 d일")
    private LocalDate medicationExecuteDate;
    private String medicationExecuteTime;
    private String storageMethod;
    private String others;
    private String medicineUrl;
    private String parentName;

    private String signUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 M월 d일")
    private LocalDate signDate;

}
