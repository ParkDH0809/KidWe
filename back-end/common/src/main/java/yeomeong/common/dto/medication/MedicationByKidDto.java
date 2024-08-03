package yeomeong.common.dto.medication;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicationByKidDto {

    private Long medicationId;
    private String kidName;


    public MedicationByKidDto(Long medicationId, String kidName){
        this.medicationId = medicationId;
        this.kidName = kidName;
    }

}
