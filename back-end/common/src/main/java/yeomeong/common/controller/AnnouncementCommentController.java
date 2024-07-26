package yeomeong.common.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.post.announcement.AnnouncementCommentCreateDto;
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

}
