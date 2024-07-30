package yeomeong.common.dto.medication;


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
}
