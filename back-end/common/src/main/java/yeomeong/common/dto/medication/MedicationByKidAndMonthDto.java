package yeomeong.common.dto.medication;


import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Setter
@Getter
public class MedicationByKidAndMonthDto {

    private Long medicationId;
    private String kidName;
    private String banName;
    private LocalDateTime medicationCreatedDateTime;
    private boolean isDeleted;

    @QueryProjection
    public MedicationByKidAndMonthDto(Long medicationId, String kidName, String banName, LocalDateTime localDateTime, boolean isDeleted ) {
        this.medicationId = medicationId;
        this.kidName = kidName;
        this.banName = banName;
        this.medicationCreatedDateTime = localDateTime;
        this.isDeleted = isDeleted;
    }
}
