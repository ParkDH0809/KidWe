package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.post.announcement.AnnouncementCommentCreateDto;
import yeomeong.common.dto.post.announcement.CommentChildDto;
import yeomeong.common.entity.post.Announcement;
import yeomeong.common.entity.post.comment.AnnouncementComment;
import yeomeong.common.entity.post.comment.Comment;
import yeomeong.common.repository.AnnouncementCommentRepository;
import yeomeong.common.repository.AnnouncementRepository;

@Service
@RequiredArgsConstructor
public class AnnouncementCommentService {

    AnnouncementRepository announcementRepository;
    AnnouncementCommentRepository announcementCommentRepository;

    /**
     * 공지사항 (대)댓글 생성하기
     * 공지사항 (대)댓글 삭제하기
     */


    //공지사항 댓글 작성하기
    public void createComment(Long announcementId, Long memberId,AnnouncementCommentCreateDto announcementCommentCreateDto){
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("해당 공지사항을 찾을 수 없습니다."));

        Comment comment = new Comment(announcementCommentCreateDto.getContent(), memberId);
        AnnouncementComment announcementComment =new AnnouncementComment(comment,announcement);


        announcement.getCommentList().add(announcementComment);
    }

    //공지사항 댓글 삭제하기
    public void deleteComment(Long announcementCommentId) {
        AnnouncementComment comment = announcementCommentRepository.findById(announcementCommentId)
                .orElseThrow(() -> new RuntimeException("해당 공지사항 댓글을 찾을 수 없습니다"));

        announcementCommentRepository.delete(comment);
    }

    //공지사항 대댓글 작성하기
    public void createCommentChildren(Long announcementCommentParentId, Long memberId, CommentChildDto commentChildDto){
        AnnouncementComment announcementComment = announcementCommentRepository.findById(announcementCommentParentId)
                .orElseThrow(() -> new RuntimeException("해당 부모 댓글을 찾을 수 없습니다."));

        Comment childComment = new Comment(commentChildDto.getContent(), memberId); //새롭게 만들어진 comment

        if(!announcementComment.getComment().isDepth()) //대댓글이 기존에 없는 친구라면
            announcementComment.getComment().setDepth(true);

        announcementComment.getComment().setCommentGroup(announcementComment.getComment().getCommentGroup()+1); //대댓글 하나 추가 됨.

        AnnouncementComment announcementCommentChild = new AnnouncementComment(childComment, announcementComment.getAnnouncement());

    }


    //공지사항 대댓글 삭제하기
}
