package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.post.announcement.AnnouncementCommentCreateDto;
import yeomeong.common.dto.post.announcement.CommentChildDto;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.Announcement;
import yeomeong.common.entity.post.comment.AnnouncementComment;
import yeomeong.common.repository.AnnouncementCommentRepository;
import yeomeong.common.repository.AnnouncementRepository;
import yeomeong.common.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AnnouncementCommentService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementCommentRepository announcementCommentRepository;
    private final MemberRepository memberRepository;
    /**
     * 공지사항 (대)댓글 생성하기
     * 공지사항 (대)댓글 삭제하기
     */


    //공지사항 댓글 작성하기
    public void createComment(Long announcementId, Long memberId,AnnouncementCommentCreateDto announcementCommentCreateDto){
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("해당 공지사항을 찾을 수 없습니다."));

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("해당 맴버가 없으세요"));

        AnnouncementComment announcementComment =new AnnouncementComment(member,announcementCommentCreateDto.getContent(),announcement);

        announcement.getCommentList().add(announcementComment);

        announcementCommentRepository.save(announcementComment);

    }

    //공지사항 (대)댓글 삭제하기
    @Transactional
    public void deleteComment(Long announcementCommentId) {
        AnnouncementComment comment = announcementCommentRepository.findById(announcementCommentId)
                .orElseThrow(() -> new RuntimeException("해당 공지사항 댓글을 찾을 수 없습니다"));

        comment.setDeleted(true);

    }

    //공지사항 대댓글 작성하기
    public void createCommentChildren(Long announcementCommentParentId, Long memberId, CommentChildDto commentChildDto){
        AnnouncementComment parentComment = announcementCommentRepository.findById(announcementCommentParentId)
                .orElseThrow(() -> new RuntimeException("해당 부모 댓글을 찾을 수 없습니다."));

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("해당 맴버가 없으세요"));


        AnnouncementComment childComment = new AnnouncementComment(member,commentChildDto.getContent(),parentComment.getAnnouncement());
        childComment.setParentComment(parentComment);

         //새롭게 만들어진 comment
        parentComment.getReplies().add(childComment);

        announcementCommentRepository.save(childComment);
    }

}
