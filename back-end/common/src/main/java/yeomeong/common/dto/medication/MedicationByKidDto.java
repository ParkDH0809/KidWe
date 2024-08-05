package yeomeong.common.dto.medication;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MedicationByKidDto {

    private Long medicationId;
    private String kidName;
    private LocalDateTime medicationCreatedDateTime;

    public MedicationByKidDto(Long medicationId, String kidName, LocalDateTime localDateTime){
        this.medicationId = medicationId;
        this.kidName = kidName;
        this.medicationCreatedDateTime = localDateTime;
    }

}
