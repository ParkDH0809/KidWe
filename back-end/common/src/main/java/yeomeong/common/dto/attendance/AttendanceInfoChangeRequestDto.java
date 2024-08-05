package yeomeong.common.dto.attendance;

import java.util.List;
import lombok.Getter;

@Getter
public class AttendanceInfoChangeRequestDto {

    Integer year;
    Integer month;
    Integer day;
    List<Long> kidIds;
    Boolean attendedToday;

    public boolean containsNull() {
        return this.year == null || this.month == null || this.day == null || this.attendedToday == null || this.kidIds.isEmpty();
    }

}
