package yeomeong.common.dto.medication;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MedicationByKidDto {

    private Long medicationId;
    private String kidName;
    private String banName;
    private LocalDateTime medicationCreatedDateTime;
    private boolean isDeleted;


    public MedicationByKidDto(Long id,String kidName, String banName, LocalDateTime medicationCreatedDateTime, boolean isDeleted) {
        this.medicationId = id;
        this.kidName = kidName;
        this.banName = banName;
        this.medicationCreatedDateTime = medicationCreatedDateTime;
        this.isDeleted = isDeleted;
    }
}
