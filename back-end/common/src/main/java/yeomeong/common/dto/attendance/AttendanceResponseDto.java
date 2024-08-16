package yeomeong.common.dto.attendance;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.Attendance;
import yeomeong.common.entity.AttendanceType;

@Getter
@Builder
public class AttendanceResponseDto {

    long attendanceId;

    long banId;
    String banName;

    long kidId;
    String kidName;

    String reason;
    String image;
    AttendanceType attendedToday;

    LocalDate date;

    public static AttendanceResponseDto toAttendanceResponseDto(Attendance attendance) {
        return AttendanceResponseDto.builder()
                .attendanceId(attendance.getId())
                .banId(attendance.getKid().getBan().getId())
                .banName(attendance.getKid().getBan().getName())
                .kidId(attendance.getKid().getId())
                .kidName(attendance.getKid().getName())
                .reason(attendance.getReason())
                .image(attendance.getKid().getPicture())
                .attendedToday(attendance.getAttendedToday())
                .date(attendance.getDate())
                .build();
    }

}
