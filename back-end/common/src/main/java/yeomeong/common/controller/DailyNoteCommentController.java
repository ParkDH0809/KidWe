package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.post.dailynote.request.DailyNoteCommentCreateRequestDto;
import yeomeong.common.dto.post.dailynote.request.DailyNoteCommentUpdateRequestDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteCommentResponseDto;
import yeomeong.common.entity.post.comment.DailyNoteComment;
import yeomeong.common.service.DailyNoteCommentService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/dailynotecomments")
@Tag(name = "알림장 댓글 API", description = "알림장 댓글 관련 API")
public class DailyNoteCommentController {
    private final DailyNoteCommentService dailyNoteCommentService;

    @PostMapping("/")
    ResponseEntity<DailyNoteCommentResponseDto> createDailyNoteComment(@RequestBody DailyNoteCommentCreateRequestDto dailyNoteCommentCreateRequestDto) {
        DailyNoteCommentResponseDto createdDailyNoteComment = dailyNoteCommentService.createDailyNoteComment(dailyNoteCommentCreateRequestDto);
        return ResponseEntity.ok(createdDailyNoteComment);
    }

    @PutMapping("/")
    ResponseEntity<DailyNoteCommentResponseDto> updateDailyNoteComment(@RequestBody DailyNoteCommentUpdateRequestDto dailyNoteCommentUpdateRequestDto) {
        DailyNoteCommentResponseDto updatedDailyNoteComment = dailyNoteCommentService.updateDailyNoteComment(dailyNoteCommentUpdateRequestDto);
        return ResponseEntity.ok(updatedDailyNoteComment);
    }

    @DeleteMapping("/{daily_note_comment_id}")
    ResponseEntity<String> deleteDailyNoteComment(@PathVariable("daily_note_comment_id") Long dailyNoteCommentId) {
        dailyNoteCommentService.deleteDailyNoteComment(dailyNoteCommentId);
        return ResponseEntity.ok("Success!!!");
    }
}
