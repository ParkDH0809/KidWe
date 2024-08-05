package yeomeong.common.dto.menu.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MenuByDayResponseDto {

    private String lunch;
    private List<String> lunchAllergies;
    private String snack;
    private List<String> snackAllergies;
    private String dinner;
    private List<String> dinnerAllergies;

}
