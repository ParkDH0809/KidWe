package yeomeong.common.dto.attendance;

import java.util.List;
import lombok.Getter;
import yeomeong.common.entity.AttendanceType;

@Getter
public class AttendanceInfoChangeRequestDto {

    Integer year;
    Integer month;
    Integer day;
    List<Long> kidIds;
    AttendanceType attendedToday;
    String reason;

    public boolean containsNull() {
        return this.year == null || this.month == null || this.day == null || this.attendedToday == null || this.kidIds.isEmpty();
    }

    public boolean containsOneKidAndReason() {
        return reason != null && !reason.isEmpty() && kidIds.size() == 1;
    }

}
