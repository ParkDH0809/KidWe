package yeomeong.common.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "일정 관리 API", description = "일정 관리 관련 API")
public class ScheduleController {

    private final ScheduleService scheduleService;


    //유치원 전체 공지사항 불러오기
    @GetMapping("all/{kindergartenId}/{date}")
    @Operation(summary = "해당 일자의 전체를 대상으로 한 일정 목록을 조회합니다.", description = "유치원 id와 date형식을 통해 일정을 해당 일정의 유치원 전체 일정을 불러옵니다.")
    public ResponseEntity<List<ScheduleByDayListDto>> getAllScheduleNoticeList(
            @PathVariable("kindergartenId") Long kindergartenId,
            @PathVariable("date") LocalDate localDate){

        return ResponseEntity.ok(scheduleService.getScheduleByAllNoticeAndDayList(kindergartenId, localDate));
    }

    //반 별 공지사항 불러오기
    @GetMapping("ban/{banId}/{date}")
    @Operation(summary = "반의 일정 목록을 조회합니다", description = "해당 반id를 통해 반의 일정 목록을 불러옵니다.")
    public ResponseEntity<List<ScheduleByDayListDto>> getScheduleByBan(
            @PathVariable("banId") Long banId,
            @PathVariable("date") LocalDate localdate){

        return ResponseEntity.ok(scheduleService.getScheduleByBanAndDayList(banId, localdate));
    }

    //스케즐 생성하기
    @PostMapping("/{memberId}")
    @Operation(summary = "일정을 생성합니다. (선생, 원장)", description = "맴버의 id와 작성한 일정을 생성합니다.")
    public ResponseEntity<Void> createSchedule(
            @PathVariable("memberId") Long memberId,
            @RequestBody CreateScheduleRequestDto requestDto){

        scheduleService.createSchedule(memberId,requestDto);

        return ResponseEntity.ok().build();
    }

    //스케줄 수정하기
    @PutMapping("/{scheduleId}")
    @Operation(summary = "일정을 수정합니다", description = "해당 일정의 id와 작성된 수정사항을 통해 일정을 수정합니다.")
    public ResponseEntity<CreateScheduleResponseDto> updateSchedule(
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody CreateScheduleRequestDto requestDto){

        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, requestDto));
    }

    //스케줄 삭제하기
    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "해당 일정을 삭제합니다.", description = "일정 id를 통해 해당 일정을 삭제합니다.")
    public ResponseEntity<Void> removeSchedule(
            @PathVariable("scheduleId") Long scheduleId){

        scheduleService.removeSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }
}
