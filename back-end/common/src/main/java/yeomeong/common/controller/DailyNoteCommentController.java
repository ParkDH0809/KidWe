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
import yeomeong.common.dto.post.dailynote.request.DailyNoteCommentRequestDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteCommentResponseDto;
import yeomeong.common.service.DailyNoteCommentService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/dailynotecomments")
@Tag(name = "알림장 댓글 API", description = "알림장 댓글 관련 API")
public class DailyNoteCommentController {
    private final DailyNoteCommentService dailyNoteCommentService;

    @Operation(summary = "알림장 (대)댓글 생성 API", description = "알림장 (대)댓글을 생성합니다. (댓글인 경우 parent_comment를 0으로, 대댓글인 경우 댓글의 id를 담아 보내주세요)")
    @PostMapping("/{member_id}/{dailynote_id}")
    ResponseEntity<DailyNoteCommentResponseDto> createDailyNoteComment(@PathVariable("member_id") Long writerId,
        @PathVariable("dailynote_id") Long dailyNoteId,
        @RequestBody DailyNoteCommentRequestDto dailyNoteCommentCreateRequestDto) {
        DailyNoteCommentResponseDto createdDailyNoteComment = dailyNoteCommentService.createDailyNoteComment(writerId, dailyNoteId, dailyNoteCommentCreateRequestDto);
        return ResponseEntity.ok(createdDailyNoteComment);
    }

    @Operation(summary = "알림장 (대)댓글 수정 API", description = "알림장 (대)댓글을 수정합니다.")
    @PutMapping("/{member_id}/{dailynote_id}/{daily_note_comment_id}")
    ResponseEntity<DailyNoteCommentResponseDto> updateDailyNoteComment(
        @PathVariable("member_id") Long writerId,
        @PathVariable("dailynote_id") Long dailyNoteId,@PathVariable("daily_note_comment_id") Long dailyNoteCommentId,
        @RequestBody String content) {
        DailyNoteCommentResponseDto updatedDailyNoteComment = dailyNoteCommentService.updateDailyNoteComment(writerId, dailyNoteId, dailyNoteCommentId, content);
        return ResponseEntity.ok(updatedDailyNoteComment);
    }

    @Operation(summary = "알림장 (대)댓글 삭제 API", description = "알림장 (대)댓글을 삭제합니다.")
    @DeleteMapping("/{member_id}/{dailynote_id}/{daily_note_comment_id}")
    ResponseEntity<DailyNoteCommentResponseDto> deleteDailyNoteComment(@PathVariable("member_id") Long writerId,
        @PathVariable("dailynote_id") Long dailyNoteId,
        @PathVariable("daily_note_comment_id") Long dailyNoteCommentId) {
        DailyNoteCommentResponseDto deletedDailyNoteComment = dailyNoteCommentService.deleteDailyNoteComment(writerId, dailyNoteId, dailyNoteCommentId);
        return ResponseEntity.ok(deletedDailyNoteComment);
    }
}
