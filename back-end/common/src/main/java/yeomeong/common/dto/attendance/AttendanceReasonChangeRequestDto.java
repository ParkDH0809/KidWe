package yeomeong.common.dto.attendance;

import lombok.Getter;

@Getter
public class AttendanceReasonChangeRequestDto {

    Integer year;
    Integer month;
    Integer day;
    Long kidId;
    String reason;

    public boolean containsNull() {
        return this.year == null || this.month == null || this.day == null || this.reason ==null;
    }

}
