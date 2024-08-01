package yeomeong.common.dto.medication;


import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
public class MedicationByKidAndMonthDto {

    private Long medicationId;
    private String kidName;
    private String banName;
    private LocalDate localDate;

    @QueryProjection
    public MedicationByKidAndMonthDto(Long medicationId, String kidName, String banName, LocalDate localDate) {
        this.medicationId = medicationId;
        this.kidName = kidName;
        this.banName = banName;
        this.localDate = localDate;
    }
}
