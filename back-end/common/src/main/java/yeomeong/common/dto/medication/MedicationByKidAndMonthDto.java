package yeomeong.common.dto.medication;


import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String kidPicture;
    private String kidName;
    private String banName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 M월 d일")
    private LocalDate medicationExecuteDate;

    @QueryProjection
    public MedicationByKidAndMonthDto(Long medicationId, String kidName, String banName, LocalDate localDate ) {
        this.medicationId = medicationId;
        this.kidName = kidName;
        this.banName = banName;
        this.medicationExecuteDate = localDate;
    }
}
