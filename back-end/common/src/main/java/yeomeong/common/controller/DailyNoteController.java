package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.post.dailynote.request.DailyNoteCreateRequestDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteResponseDto;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.service.DailyNoteService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/dailynotes")
@Tag(name = "알림장 API", description = "알림장 관련 API")
public class DailyNoteController {

    private final DailyNoteService dailyNoteService;

    @PostMapping("/")
    public DailyNoteResponseDto createDailyNote(@RequestBody DailyNoteCreateRequestDto dailyNoteCreateRequestDto) {
        return dailyNoteService.createDailyNote(dailyNoteCreateRequestDto);
    }

    @GetMapping("/{kid_id}/{year}/{month}")
    public List<DailyNoteResponseDto> getDailyNotes(@PathVariable("kid_id") Long kidId,
        @PathVariable("year") String year,
        @PathVariable("year") String month) {
        String yearAndMonth = year + "-" + month;
        return dailyNoteService.getDailyNotes(kidId, yearAndMonth);
    }

    @GetMapping("/{dailynote_id}")
    public DailyNoteResponseDto getDailyNote(@PathVariable("dailynote_id") Long id) {
        return dailyNoteService.getDailyNote(id);
    }

    @PutMapping("/{dailynote_id}")
    public DailyNote updateDailyNote(@PathVariable("dailynote_id") Long id,
        @RequestBody DailyNote dailyNote) {
        return dailyNoteService.updateDailyNote(id, dailyNote);
    }

    @DeleteMapping("/{dailynote_id}")
    public void deleteDailyNote(@PathVariable("dailynote_id") Long id) {
        dailyNoteService.deleteDailyNote(id);
    }
}
