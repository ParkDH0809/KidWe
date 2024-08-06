package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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
import yeomeong.common.dto.post.dailynote.request.DailyNoteCreateRequestDto;
import yeomeong.common.dto.post.dailynote.request.DailyNoteUpdateRequestDto;
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
    @PostMapping
    public ResponseEntity<DailyNoteResponseDto> createDailyNote(@RequestBody DailyNoteCreateRequestDto dailyNoteCreateRequestDto) {
        DailyNoteResponseDto createdDailyNote = dailyNoteService.createDailyNote(dailyNoteCreateRequestDto);
        return ResponseEntity.ok(createdDailyNote);
    }

    @Operation(summary = "월별 알림장 조회 API", description = "월별로 알림장을 조회해 List로 반환합니다.")
    @GetMapping("/{member_id}/{kid_id}/{year}/{month}")
    public ResponseEntity<DailyNoteListResponseDto> getDailyNotes(@PathVariable("member_id") Long memberId,
        @PathVariable("kid_id") Long kidId,
        @PathVariable("year") String year,
        @PathVariable("month") String month) {
        String yearAndMonth = year + "-" + month;
        return ResponseEntity.ok(dailyNoteService.getDailyNotes(memberId, kidId, yearAndMonth));
    }

    @Operation(summary = "특정 알림장 상세정보 조회 API", description = "특정 알림장의 상세정보를 반환합니다.")
    @GetMapping("/{dailynote_id}")
    public ResponseEntity<DailyNoteResponseDto> getDailyNote(@PathVariable("dailynote_id") Long id) {
        return ResponseEntity.ok(dailyNoteService.getDailyNote(id));
    }

    @Operation(summary = "알림장 수정 API", description = "전송시간이 지나지 않은 알림장에 한정해 알림장을 수정합니다.")
    @PutMapping
    public ResponseEntity<DailyNoteResponseDto> updateDailyNote(@RequestBody DailyNoteUpdateRequestDto dailyNoteRequestDto) {
        return ResponseEntity.ok(dailyNoteService.updateDailyNote(dailyNoteRequestDto));
    }

    @Operation(summary = "알림장 삭제 API", description = "알림장을 삭제합니다.")
    @DeleteMapping("/{dailynote_id}")
    public ResponseEntity<Void> deleteDailyNote(@PathVariable("dailynote_id") Long id) {
        dailyNoteService.deleteDailyNote(id);
        return ResponseEntity.ok().build();
    }
}
