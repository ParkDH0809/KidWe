package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.attendance.AttendanceResponseDto;
import yeomeong.common.service.AttendanceService;

@RestController
@RequestMapping("/attendances")
@Tag(name = "출석 API", description = "출석 관련 API")
public class AttendanceController {

    final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/{banId}/{year}/{month}/{day}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendancesByBanId(
        @PathVariable Long banId, @PathVariable Integer year, @PathVariable Integer month, @PathVariable Integer day) {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceService.getAttendancesByBanId(banId, LocalDate.of(year, month, day)));
    }

}
