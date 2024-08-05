package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.entity.post.comment.DailyNoteComment;
import yeomeong.common.service.DailyNoteCommentService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/dailynotecomments")
@Tag(name = "알림장 댓글 API", description = "알림장 댓글 관련 API")
public class DailyNoteCommentController {
    private final DailyNoteCommentService dailyNoteCommentService;

    @PostMapping("/")
    ResponseEntity<DailyNoteComment> createDailyNoteComment(@RequestBody DailyNoteComment dailyNoteComment) {
        DailyNoteComment createdDailyNoteComment = dailyNoteCommentService.createDailyNoteComment(dailyNoteComment);
        return ResponseEntity.ok(createdDailyNoteComment);
    }

    @PutMapping("/")
    ResponseEntity<DailyNoteComment> updateDailyNoteComment(@RequestBody DailyNoteComment dailyNoteComment) {
        DailyNoteComment updatedDailyNoteComment = dailyNoteCommentService.updateDailyNoteComment(dailyNoteComment);
        return ResponseEntity.ok(updatedDailyNoteComment);
    }

    @DeleteMapping("/")
    ResponseEntity<Void> deleteDailyNoteComment(@RequestBody DailyNoteComment dailyNoteComment) {
        dailyNoteCommentService.deleteDailyNoteComment(dailyNoteComment);
        return ResponseEntity.noContent().build();
    }
}
