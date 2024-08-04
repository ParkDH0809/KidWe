package yeomeong.common.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.attendance.AttendanceInfoChangeRequestDto;
import yeomeong.common.dto.attendance.AttendanceResponseDto;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.AttendanceRepository;
import yeomeong.common.repository.BanRepository;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, BanRepository banRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public List<AttendanceResponseDto> getAttendancesByBanId(Long banId, LocalDate localDate) {
        List<AttendanceResponseDto> attendanceResponseDtos = new ArrayList<>();
        attendanceRepository.findAttendancesByBanIdAndDate(banId, localDate).forEach(attendance ->
            attendanceResponseDtos.add(AttendanceResponseDto.toAttendanceResponseDto(attendance))
        );
        return attendanceResponseDtos;
    }

    public void updateAttendances(AttendanceInfoChangeRequestDto changeRequestDto) {
        if(changeRequestDto.containsNull()) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
        LocalDate localDate = LocalDate.of(changeRequestDto.getYear(), changeRequestDto.getMonth(), changeRequestDto.getDay());
        for (Long kidId : changeRequestDto.getKidIds()) {
            attendanceRepository.updateKidsAttendanceState(kidId, localDate, changeRequestDto.getAttendedToday());
        }
    }

}