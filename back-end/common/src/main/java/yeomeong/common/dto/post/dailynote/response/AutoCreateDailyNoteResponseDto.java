package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.Schedule;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.gtype;

@Getter
public class AutoCreateDailyNoteResponseDto {
    private Long teacherId;

    private String teacherName;

    private String kidName;
    private gtype gender;
    private LocalDate birthday;

    private String kindergartenName;
    private String banName;

    @Getter
    @NoArgsConstructor
    private class ScheduleInfo{
        private String keyword;
        private String content;

        @Builder
        public ScheduleInfo(Schedule schedule) {
            this.keyword = schedule.getKeyword();
            this.content = schedule.getContent();
        }
    }

    private List<ScheduleInfo> schedules;

    @Builder
    public AutoCreateDailyNoteResponseDto(Member teacher, Kid kid, List<Schedule> schedules) {
        this.teacherId = teacher.getId();
        this.teacherName = teacher.getName();

        this.kidName = kid.getName();
        this.gender = kid.getGender();
        this.birthday = kid.getBirthday();

        this.kindergartenName = teacher.getKindergarten().getName();
        this.banName = teacher.getBan().getName();

        this.schedules = new ArrayList<ScheduleInfo>();
        for(Schedule schedule : schedules){
            this.schedules.add(new ScheduleInfo(schedule));
        }
    }
}
