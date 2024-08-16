package yeomeong.common.dto.schedule;

import lombok.Data;
import yeomeong.common.entity.Schedule;

import java.time.LocalDate;

@Data
public class CreateScheduleRequestDto {

    private String keyword;
    private String content;
    private LocalDate localDate;
    private Schedule.ScheduleType scheduleType;
}
