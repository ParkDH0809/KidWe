package yeomeong.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.post.announcement.AnnouncementCreateDto;
import yeomeong.common.dto.post.announcement.AnnouncementDetailDto;
import yeomeong.common.dto.post.announcement.AnnouncementListDto;
import yeomeong.common.entity.jpa.member.Member;
import yeomeong.common.entity.jpa.member.rtype;
import yeomeong.common.entity.jpa.post.Announcement;
import yeomeong.common.repository.jpa.AnnouncementRepository;
import yeomeong.common.repository.jpa.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final MemberRepository memberRepository;

    /**
     * 유치원별 공지사항 생성하기 (원장님)
     * 반 별 공지사항 생성하기 (선생님)
     */
    public void createAnnouncementByKindergarten(Long memberId, AnnouncementCreateDto announcementCreateDto){
        Announcement announcement =new Announcement(announcementCreateDto.getPost(),
                memberRepository.findOne(memberId));

        announcementRepository.save(announcement);
    }

     /** 유치원별 공지사항 조회하기
     * 반별 공지사항 조회하기
     * 공지사항 조회하기
     */

     public void getAnnouncementList(Long memberId){
         Member member = memberRepository.findOne(memberId);

//         if(member.getRole() == rtype.DIRECTOR){
//           return announcementRepository.getAnnouncementByDirector(member.getBan().getKindergarten().getId());
//         } //원장님일 때 해당 유치원 공지사항 모두 가져오기
//         else {
//            return announcementRepository.getAnnouncementByParent(member.getBan().getId());
//         } //선생님이나 학부모일 땐 해당 반 다 가져오기
     }


    public AnnouncementDetailDto getAnnouncementDetail(Long announcementId) {

         Announcement announcement = announcementRepository.findById(announcementId)
                 .orElseThrow(() -> new RuntimeException("해당 공지사항을 찾을 수 없습니다."));

         return  new AnnouncementDetailDto(
                 announcement.getMember().getId(),
                 announcement.getPost(),
                 announcement.getCommentList());

    }

    public void updateAnnouncement(Long announcementId, AnnouncementCreateDto announcementCreateDto) {

         Announcement announcement = announcementRepository.findById(announcementId)
                 .orElseThrow(() -> new RuntimeException("해당 공지사항을 수정할 수 없습니다."));

         announcement.setPost(announcementCreateDto.getPost());
         announcementRepository.save(announcement);
    }
}
