package yeomeong.common.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.post.announcement.AnnouncementCommentCreateDto;
import yeomeong.common.dto.post.announcement.CommentChildDto;
import yeomeong.common.service.AnnouncementCommentService;

@RestController
@RequestMapping("/announcement-comment")
@RequiredArgsConstructor
public class AnnouncementCommentController {

    private final AnnouncementCommentService announcementCommentService;


    //공지사항-댓글 작성하기
    @PostMapping("/{announcement_id}/{member_id}")
    public ResponseEntity<Void> createAnnouncementComment(
            @PathVariable("announcement_id") Long announcementId,
            @PathVariable("member_id") Long memberId,
            @RequestBody AnnouncementCommentCreateDto announcementCommentCreateDto){

        announcementCommentService.createComment(announcementId, memberId ,announcementCommentCreateDto);

        return ResponseEntity.ok().build();
    }

    //공지사항- 댓글 삭제하기
    @DeleteMapping("/{announcement_comment_id}")
    public ResponseEntity<Void> deleteAnnouncementComment(
            @PathVariable("announcement_comment_id") Long commentId) {

        announcementCommentService.deleteComment(commentId);

        return ResponseEntity.ok().build();
    }

    //공지사항 - 대댓글 작성하기
    @PostMapping("/{announcement_comment_id}/{member_id}")
    public ResponseEntity<Void> createCommentChild(
            @PathVariable("announcement_comment_id") Long commentParentId,
            @PathVariable("member_id") Long memberId,
            @RequestBody CommentChildDto commentChildDto){

        announcementCommentService.createCommentChildren(commentParentId, memberId ,commentChildDto);

        return ResponseEntity.ok().build();
    }


    //공지사항 - 대댓글 삭제하기

}
