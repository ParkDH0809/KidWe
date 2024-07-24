package yeomeong.common.dto.menu.request;


import lombok.Data;

import java.time.LocalDate;

@Data
public class MenuCreateDto {

    private String lunch;
    private String lunchAllergies;
    private String snack;
    private String snackAllergies;
    private String dinner;
    private String dinnerAllergies;
    private LocalDate menuDate;
}
