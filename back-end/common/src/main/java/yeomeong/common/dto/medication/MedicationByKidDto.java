package yeomeong.common.dto.medication;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
