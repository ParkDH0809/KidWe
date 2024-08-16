package yeomeong.common.dto.menu.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MenuByDayRequestDto {

    private Long kindergartenId;
    private LocalDate day;

}
