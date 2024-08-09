package yeomeong.common.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.MemoResponseDto;
import yeomeong.common.service.MemoService;
import yeomeong.common.service.OpenAiService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/dailynotecontents")
public class AutoDailyNoteController {
    private final OpenAiService openAIService;
    private final MemoService memoService;

    @GetMapping("/{teacher_id}/{year}/{month}/{date}/{kid_id}")
    public ResponseEntity<String> getAutoDailyNote(@PathVariable("teacher_id") Long teacherId,
        @PathVariable("year") String year,
        @PathVariable("month") String month,
        @PathVariable("day") String day,
        @PathVariable("kid_id") Long kidId) {

        String date = year + "-" + month + "-" + day;
        List<MemoResponseDto> memoResponseDtos = memoService.getMemosByTeacherIdAndDateAndKidId(teacherId, date, kidId);
        // 메모에서 정보를 추출하고, 해당 메모를 바탕으로 알림장을 생성하는 로직
        String role = "user";
        String prompt = "";
        return ResponseEntity.ok(openAIService.generateText(role, prompt));
    }
}
