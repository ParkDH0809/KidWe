package yeomeong.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.service.OpenAiService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/openais")
public class OpenAIController {
    private final OpenAiService openAIService;

    @GetMapping("/{teacher_id}/{prompt}")
    public ResponseEntity<String> getAutoDailyNote(@PathVariable("teacher_id") String teacherId,
        @PathVariable("prompt") String prompt) {
        return ResponseEntity.ok(openAIService.generateText(prompt));
    }
}
