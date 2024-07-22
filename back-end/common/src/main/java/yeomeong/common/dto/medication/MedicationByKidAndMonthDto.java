package yeomeong.common.dto.medication;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class MedicationByKidAndMonthDto {


    private Date medicationExecuteDate;
    private String kidName;
}
