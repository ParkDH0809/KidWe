package yeomeong.common.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.schedule.CreateScheduleRequestDto;
import yeomeong.common.dto.schedule.CreateScheduleResponseDto;
import yeomeong.common.dto.schedule.ScheduleByDayListDto;
import yeomeong.common.service.ScheduleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;


    //유치원 전체 공지사항 불러오기
    @GetMapping("all/{kindergarten_id}/{date}")
    public ResponseEntity<List<ScheduleByDayListDto>> getAllScheduleNoticeList(
            @PathVariable("kindergarten_id") Long kindergartenId,
            @PathVariable("date") LocalDate localDate){

        return ResponseEntity.ok(scheduleService.getScheduleByallNoticeAndDayList(kindergartenId, localDate));
    }

    //반 별 공지사항 불러오기
    @GetMapping("ban/{ban_id}/{date}")
    public ResponseEntity<List<ScheduleByDayListDto>> getScheduleByBan(
            @PathVariable("ban_id") Long banId,
            @PathVariable("date") LocalDate localdate){

        return ResponseEntity.ok(scheduleService.getScheduleByBanAndDayList(banId, localdate));
    }

    //스케즐 생성하기
    @PostMapping("/{member_id}")
    public ResponseEntity<Void> createSchedule(
            @PathVariable("member_id") Long memberId,
            @RequestBody CreateScheduleRequestDto requestDto){

        scheduleService.createSchedule(memberId,requestDto);

        return ResponseEntity.ok().build();
    }

    //스케줄 수정하기
    @PutMapping("/{schedule_id}")
    public ResponseEntity<CreateScheduleResponseDto> updateSchedule(
            @PathVariable("schedule_id") Long scheduleId,
            @RequestBody CreateScheduleRequestDto requestDto){

        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, requestDto));
    }

    //스케줄 삭제하기
    @DeleteMapping("/{schedule_id}")
    public ResponseEntity<Void> removeSchedule(
            @PathVariable("schedule_id") Long scheduleId){

        scheduleService.removeSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }
}
