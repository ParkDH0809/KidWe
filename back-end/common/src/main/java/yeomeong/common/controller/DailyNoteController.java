package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.service.DailyNoteService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/dailynote")
@Tag(name = "알림장", description = "알림장 관련 API")
public class DailyNoteController {
    private final DailyNoteService dailyNoteService;

    @PostMapping("/")
    public DailyNote createDailyNote(@RequestBody DailyNote dailyNote) {
        return dailyNoteService.createDailyNote(dailyNote);
    }

    @GetMapping("/{kid_id}/{year}/{month}")
    public List<DailyNote> getDailyNotes(@PathVariable("kid_id") Long kidId,
                                            @PathVariable("year") String year,
                                            @PathVariable("year") String month){
        return null;
    }
}
