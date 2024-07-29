package yeomeong.common.dto.menu.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MenuByDayResponseDto {

    private String lunch;
    private String lunchAllergies;
    private String snack;
    private String snackAllergies;
    private String dinner;
    private String dinnerAllergies;
}
