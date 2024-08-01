package yeomeong.common.dto.schedule;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
public class CreateScheduleResponseDto {

    private String keyword;
    private String content;
    private LocalDate localDate;
    private LocalTime localTime;

    public CreateScheduleResponseDto(String keyword, String content, LocalDate localDate, LocalTime localTime) {
        this.keyword = keyword;
        this.content = content;
        this.localDate = localDate;
        this.localTime = localTime;
    }
}
