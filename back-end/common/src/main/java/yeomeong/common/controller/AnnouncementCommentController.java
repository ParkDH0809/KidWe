package yeomeong.common.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.post.announcement.AnnouncementCommentCreateDto;
import yeomeong.common.dto.post.announcement.CommentChildDto;
import yeomeong.common.service.AnnouncementCommentService;

@RestController
@RequestMapping("/announcement-comment")
@Tag(name = "게시판 댓글 API", description = "게시판 댓글 관련 API")
@RequiredArgsConstructor
public class AnnouncementCommentController {

    private final AnnouncementCommentService announcementCommentService;


    //공지사항-댓글 작성하기
    @PostMapping("/{announcement_id}/{member_id}")
    @Operation(summary = "공지사항의 댓글을 작성합니다", description = "공지사항의 id와 회원의 id를 받아와서 공지사항 댓글을 작성합니다.")
    public ResponseEntity<Void> createAnnouncementComment(
            @PathVariable("announcement_id") Long announcementId,
            @PathVariable("member_id") Long memberId,
            @RequestBody AnnouncementCommentCreateDto announcementCommentCreateDto){

        announcementCommentService.createComment(announcementId, memberId ,announcementCommentCreateDto);

        return ResponseEntity.ok().build();
    }

    //공지사항- 댓글 삭제하기
    @DeleteMapping("/{announcement_comment_id}")
    @Operation(summary = "공지사항의 (대)댓글을 삭제합니다", description = "공지사항 댓글의 id를 통해 해당 댓글을 삭제합니다.")
    public ResponseEntity<Void> deleteAnnouncementComment(
            @PathVariable("announcement_comment_id") Long commentId) {

        announcementCommentService.deleteComment(commentId);

        return ResponseEntity.ok().build();
    }

    //공지사항 - 대댓글 작성하기
    @PostMapping("/{announcement_comment_id}/{member_id}")
    @Operation(summary = "공지사항의 대댓글을 작성합니다", description = "공지사항 댓글의 id와 member의 id를 통해 대댓글을 작성합니다.")
    public ResponseEntity<Void> createCommentChild(
            @PathVariable("announcement_comment_id") Long commentParentId,
            @PathVariable("member_id") Long memberId,
            @RequestBody CommentChildDto commentChildDto){

        announcementCommentService.createCommentChildren(commentParentId, memberId ,commentChildDto);

        return ResponseEntity.ok().build();
    }


}
