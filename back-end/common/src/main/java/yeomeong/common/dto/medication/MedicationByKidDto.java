package yeomeong.common.dto.medication;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class MedicationByKidDto {

    private Long medicationId;
    private String kidName;
    private String banName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 M월 d일")
    private LocalDate medicationExecuteDate;
    private boolean isDeleted;


    public MedicationByKidDto(Long id,String kidName, String banName, LocalDate medicationExecuteDate, boolean isDeleted) {
        this.medicationId = id;
        this.kidName = kidName;
        this.banName = banName;
        this.medicationExecuteDate = medicationExecuteDate;
        this.isDeleted = isDeleted;
    }
}
