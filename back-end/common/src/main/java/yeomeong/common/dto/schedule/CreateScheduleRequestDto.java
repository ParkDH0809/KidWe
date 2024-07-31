package yeomeong.common.dto.schedule;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateScheduleRequestDto {

    private String keyword;
    private String content;
    private LocalDate localDate;
}
