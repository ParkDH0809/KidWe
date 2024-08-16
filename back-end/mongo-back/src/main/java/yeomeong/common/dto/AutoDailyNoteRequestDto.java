package yeomeong.common.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutoDailyNoteRequestDto {
    private Long teacherId;
    private String teacherName;

    private Long kidId;
    private String kidName;
    private String gender;
    private LocalDate birthday;

    private String kindergartenName;
    private String banName;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ScheduleInfo{
        private String keyword;
        private String content;
    }

    private List<ScheduleInfo> schedules;
}
