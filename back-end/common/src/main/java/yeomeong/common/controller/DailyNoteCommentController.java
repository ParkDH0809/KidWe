package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import yeomeong.common.service.DailyNoteCommentService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/dailynotecomments")
@Tag(name = "알림장 댓글 API", description = "알림장 댓글 관련 API")
public class DailyNoteCommentController {
    private final DailyNoteCommentService dailyNoteCommentService;

    @Operation(summary = "알림장 (대)댓글 생성 API", description = "알림장 (대)댓글을 생성합니다. (댓글인 경우 parent_comment를 null로, 대댓글인 경우 댓글의 id를 담아 보내주세요)")
    @PostMapping("/")
    ResponseEntity<DailyNoteCommentResponseDto> createDailyNoteComment(@RequestBody DailyNoteCommentCreateRequestDto dailyNoteCommentCreateRequestDto) {
        DailyNoteCommentResponseDto createdDailyNoteComment = dailyNoteCommentService.createDailyNoteComment(dailyNoteCommentCreateRequestDto);
        return ResponseEntity.ok(createdDailyNoteComment);
    }

    @Operation(summary = "알림장 (대)댓글 수정 API", description = "알림장 (대)댓글을 수정합니다.")
    @PutMapping("/")
    ResponseEntity<DailyNoteCommentResponseDto> updateDailyNoteComment(@RequestBody DailyNoteCommentUpdateRequestDto dailyNoteCommentUpdateRequestDto) {
        DailyNoteCommentResponseDto updatedDailyNoteComment = dailyNoteCommentService.updateDailyNoteComment(dailyNoteCommentUpdateRequestDto);
        return ResponseEntity.ok(updatedDailyNoteComment);
    }

    @Operation(summary = "알림장 (대)댓글 삭제 API", description = "알림장 (대)댓글을 삭제합니다.")
    @DeleteMapping("/{daily_note_comment_id}")
    ResponseEntity<String> deleteDailyNoteComment(@PathVariable("daily_note_comment_id") Long dailyNoteCommentId) {
        dailyNoteCommentService.deleteDailyNoteComment(dailyNoteCommentId);
        return ResponseEntity.ok("Success!!!");
    }
}
