package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.post.announcement.AnnouncementCommentCreateDto;
import yeomeong.common.dto.post.announcement.CommentChildDto;
import yeomeong.common.entity.jpa.post.Announcement;
import yeomeong.common.entity.jpa.post.comment.AnnouncementComment;
import yeomeong.common.repository.jpa.AnnouncementCommentRepository;
import yeomeong.common.repository.jpa.AnnouncementRepository;

@Service
@RequiredArgsConstructor
@Transactional
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

        AnnouncementComment announcementComment =new AnnouncementComment(memberId,announcementCommentCreateDto.getContent(),announcement);

        announcement.getCommentList().add(announcementComment);

        announcementCommentRepository.save(announcementComment);

    }

    //공지사항 (대)댓글 삭제하기
    public void deleteComment(Long announcementCommentId) {
        AnnouncementComment comment = announcementCommentRepository.findById(announcementCommentId)
                .orElseThrow(() -> new RuntimeException("해당 공지사항 댓글을 찾을 수 없습니다"));

        announcementCommentRepository.delete(comment);
    }

    //공지사항 대댓글 작성하기
    public void createCommentChildren(Long announcementCommentParentId, Long memberId, CommentChildDto commentChildDto){
        AnnouncementComment parentComment = announcementCommentRepository.findById(announcementCommentParentId)
                .orElseThrow(() -> new RuntimeException("해당 부모 댓글을 찾을 수 없습니다."));

        AnnouncementComment childComment = new AnnouncementComment(memberId,commentChildDto.getContent(),parentComment.getAnnouncement());
        childComment.setParentComment(parentComment);

         //새롭게 만들어진 comment
        parentComment.getReplies().add(childComment);

        announcementCommentRepository.save(childComment);
    }

}
