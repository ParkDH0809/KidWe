package yeomeong.common.controller;

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
import yeomeong.common.dto.post.dailynote.response.DailyNoteResponseDto;
import yeomeong.common.service.DailyNoteService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/dailynotes")
@Tag(name = "알림장 API", description = "알림장 관련 API")
public class DailyNoteController {

    private final DailyNoteService dailyNoteService;

    @PostMapping("/")
    public ResponseEntity<DailyNoteResponseDto> createDailyNote(@RequestBody DailyNoteCreateRequestDto dailyNoteCreateRequestDto) {
        return ResponseEntity.ok(dailyNoteService.createDailyNote(dailyNoteCreateRequestDto));
    }

    @GetMapping("/{member_id}/{kid_id}/{year}/{month}")
    public ResponseEntity<List<DailyNoteResponseDto>> getDailyNotes(@PathVariable("member_id") Long memberId,
        @PathVariable("kid_id") Long kidId,
        @PathVariable("year") String year,
        @PathVariable("year") String month) {
        String yearAndMonth = year + "-" + month;
        return ResponseEntity.ok(dailyNoteService.getDailyNotes(memberId, kidId, yearAndMonth));
    }

    @GetMapping("/{dailynote_id}")
    public ResponseEntity<DailyNoteResponseDto> getDailyNote(@PathVariable("dailynote_id") Long id) {
        return ResponseEntity.ok(dailyNoteService.getDailyNote(id));
    }

    @PutMapping("/")
    public ResponseEntity<DailyNoteResponseDto> updateDailyNote(@RequestBody DailyNoteUpdateRequestDto dailyNoteRequestDto) {
        return ResponseEntity.ok(dailyNoteService.updateDailyNote(dailyNoteRequestDto));
    }

    @DeleteMapping("/{dailynote_id}")
    public ResponseEntity<Void> deleteDailyNote(@PathVariable("dailynote_id") Long id) {
        dailyNoteService.deleteDailyNote(id);
        return ResponseEntity.ok().build();
    }
}
