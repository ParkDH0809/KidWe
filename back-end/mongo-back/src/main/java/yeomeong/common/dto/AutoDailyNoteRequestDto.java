package yeomeong.common.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class AutoDailyNoteRequestDto {
    private Long teacherId;

    private String teacherName;

    private String kidName;
    private String gender;
    private LocalDate birthday;

    private String kindergartenName;
    private String banName;

    @Getter
    private class ScheduleInfo{
        private String keyword;
        private String content;
    }

    private List<ScheduleInfo> schedules;
}
