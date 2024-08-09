package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.post.dailynote.request.DailyNoteRequestDto;
import yeomeong.common.dto.post.dailynote.response.AutoCreateDailyNoteResponseDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteListResponseDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteResponseDto;
import yeomeong.common.service.DailyNoteService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/dailynotes")
@Tag(name = "알림장 API", description = "알림장 관련 API")
public class DailyNoteController {

    private final DailyNoteService dailyNoteService;

    @Operation(summary = "알림장 생성 API", description = "알림장을 생성합니다.")
    @PostMapping("/{member_id}")
    public ResponseEntity<DailyNoteResponseDto> createDailyNote(
        @PathVariable("member_id") Long writerId,
        @RequestBody DailyNoteRequestDto dailyNoteCreateRequestDto) {
        DailyNoteResponseDto createdDailyNote = dailyNoteService.createDailyNote(writerId, dailyNoteCreateRequestDto);
        return ResponseEntity.ok(createdDailyNote);
    }

    @Operation(summary = "월별 알림장 조회 API - 학부모용", description = "학부모 : kid_id로 월별 알림장을 조회해 List로 반환합니다.")
    @GetMapping("/kid/{kid_id}/{member_id}/{year}/{month}")
    public ResponseEntity<DailyNoteListResponseDto> getDailyNotesByKid(@PathVariable("kid_id") Long kidId,
        @PathVariable("member_id") Long guardianId,
        @PathVariable("year") String year,
        @PathVariable("month") String month) {
        String yearAndMonth = year + "-" + month;
        DailyNoteListResponseDto dailyNoteListResponseDto = dailyNoteService.getDailyNotesByKidId(guardianId, kidId, yearAndMonth);
        return ResponseEntity.ok(dailyNoteListResponseDto);
    }

    @Operation(summary = "월별 알림장 조회 API - 선생님, 원장님 용", description = "선생, 원장 : ban_id로 월별 알림장을 조회해 List로 반환합니다.")
    @GetMapping("/ban/{ban_id}/{member_id}/{year}/{month}")
    public ResponseEntity<DailyNoteListResponseDto> getDailyNotesByBan(@PathVariable("ban_id") Long banId,
        @PathVariable("member_id") Long writerId,
        @PathVariable("year") String year,
        @PathVariable("month") String month) {
        String yearAndMonth = year + "-" + month;
        DailyNoteListResponseDto dailyNoteListResponseDto = dailyNoteService.getDailyNotesByBanId(writerId, banId, yearAndMonth);
        return ResponseEntity.ok(dailyNoteListResponseDto);
    }

    @Operation(summary = "특정 알림장 상세정보 조회 API", description = "특정 알림장의 상세정보를 반환합니다.")
    @GetMapping("/{member_id}/{dailynote_id}")
    public ResponseEntity<DailyNoteResponseDto> getDailyNote(@PathVariable("member_id") Long writerId,
        @PathVariable("dailynote_id") Long id) {
        return ResponseEntity.ok(dailyNoteService.getDailyNote(writerId, id));
    }

    @Operation(summary = "알림장 수정 API", description = "전송시간이 지나지 않은 알림장에 한정해 알림장을 수정합니다.")
    @PutMapping("/{member_id}/{dailynote_id}")
    public ResponseEntity<DailyNoteResponseDto> updateDailyNote(@PathVariable("member_id") Long writerId,
        @PathVariable("dailynote_id") Long id,
        @RequestBody DailyNoteRequestDto dailyNoteRequestDto) {
        return ResponseEntity.ok(dailyNoteService.updateDailyNote(writerId, id, dailyNoteRequestDto));
    }

    @Operation(summary = "알림장 삭제 API", description = "알림장을 삭제합니다.")
    @DeleteMapping("/{member_id}/{dailynote_id}")
    public ResponseEntity<Void> deleteDailyNote(@PathVariable("member_id") Long writerId,
        @PathVariable("dailynote_id") Long id) {
        dailyNoteService.deleteDailyNote(writerId, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "알림장 자동생성을 위해 필요한 정보 요청 API", description = "응답받은 데이터로 back-mongo 서버에 전달해주세요")
    @GetMapping("/{teacher_id}/{kid_id}")
    public ResponseEntity<AutoCreateDailyNoteResponseDto> getInfoForAutoCreateDailyNote(@PathVariable("teacher_id") Long teacherId,
        @PathVariable("kid_id") Long kidId) {
        AutoCreateDailyNoteResponseDto autoCreateDailyNoteResponseDto = dailyNoteService.getInfoForAutoCreateDailyNote(teacherId, kidId);
        return ResponseEntity.ok(autoCreateDailyNoteResponseDto);
    }
}
