package yeomeong.common.dto.schedule;


import lombok.AllArgsConstructor;
import lombok.Data;
import yeomeong.common.entity.Schedule;

@Data

public class ScheduleByDayListDto {


    private Long scheduleId;
    private String keyWord;
    private String content;
    private Schedule.ScheduleType type;


    public ScheduleByDayListDto(Long scheduleId, String keyWord, String content, Schedule.ScheduleType type) {
        this.scheduleId = scheduleId;
        this.keyWord = keyWord;
        this.content = content;
        this.type = type;
    }
}
