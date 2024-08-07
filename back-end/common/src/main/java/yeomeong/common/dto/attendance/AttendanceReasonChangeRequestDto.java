package yeomeong.common.dto.attendance;

import lombok.Getter;

@Getter
public class AttendanceReasonChangeRequestDto {

    Long kidId;
    Integer year;
    Integer month;
    Integer day;
    String reason;

    public boolean containsNull() {
        return this.year == null || this.month == null || this.day == null || this.reason ==null;
    }

}
