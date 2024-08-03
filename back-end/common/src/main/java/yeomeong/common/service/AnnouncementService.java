package yeomeong.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.post.announcement.*;

import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.entity.post.Announcement;
import yeomeong.common.entity.post.Vote;
import yeomeong.common.entity.post.VoteItem;
import yeomeong.common.entity.post.comment.AnnouncementComment;
import yeomeong.common.repository.AnnouncementRepository;
import yeomeong.common.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final MemberRepository memberRepository;

    /**
     * 유치원별 공지사항 생성하기 (원장님)
     * 반 별 공지사항 생성하기 (선생님)
     */
    @Transactional
    public void createAnnouncementByKindergarten(Long memberId, AnnouncementCreateDto announcementCreateDto){
        Announcement announcement =new Announcement(announcementCreateDto.getPost(),
                memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("해당 멤버를 찾을 수 없습니다.")));

        announcement.setStored(false);

        announcementRepository.save(announcement);
    }

     /** 유치원별 공지사항 조회하기
     * 반별 공지사항 조회하기
     * 공지사항 조회하기
     */
     public List<AnnouncementListDto> getAnnouncementList(Long memberId){
         Member member = memberRepository.findById(memberId)
                 .orElseThrow(() -> new RuntimeException("해당 멤버를 찾을 수 없습니다."));

         List<AnnouncementListDto> announcementDtoList = new ArrayList<>();

         if(member.getRole() == rtype.ROLE_DIRECTOR){ //원장님일 때 해당 유치원 공지사항 모두 가져오기
             List<AnnouncementListDto> announcementByAll = announcementRepository.getAnnouncementByAll(member.getBan().getKindergarten().getId());
             List<AnnouncementListDto> announcementByAllBan = announcementRepository.getAnnouncementByAllBan(member.getBan().getKindergarten().getId());

             announcementDtoList.addAll(announcementByAll);
             announcementDtoList.addAll(announcementByAllBan);

         }
         else {
             List<AnnouncementListDto> announcementByAll = announcementRepository.getAnnouncementByAll(member.getBan().getKindergarten().getId());
             List<AnnouncementListDto> announcementByBan = announcementRepository.getAnnouncementByAllBan(member.getBan().getId());

             announcementDtoList.addAll(announcementByAll);
             announcementDtoList.addAll(announcementByBan);

         } //선생님이나 학부모일 땐 해당 반 + 전체 공지 다 가져오기

         for(AnnouncementListDto announcementOne : announcementDtoList){
             if(announcementOne.getMemberBan() == null){
                 announcementOne.setMemberBan("전체 공지");
             }
         }
         announcementDtoList.sort((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate())); //시간 별 정렬

         return announcementDtoList;
     }


    public AnnouncementDetailDto getAnnouncementDetail(Long announcementId) {

         Announcement announcement = announcementRepository.findById(announcementId)
                 .orElseThrow(() -> new RuntimeException("해당 공지사항을 찾을 수 없습니다."));

        List<AnnouncementCommentDto> announcementCommentDto = new ArrayList<>();



        for(AnnouncementComment announcementComment : announcement.getCommentList()) {

            AnnouncementComment parentComment = announcementComment.getParentComment();

            announcementCommentDto.add(new AnnouncementCommentDto(announcementComment.getId(),
                    parentComment != null ? parentComment.getId() : null,
                    announcement.getMember().getName(),
                    announcementComment.getContent(),
                    announcementComment.getLocalDateTime()));
        }

        List<VoteItemDto> voteItemDtoList = new ArrayList<>();
        Vote vote =  announcement.getVote();


        if (vote != null) {
            for (VoteItem voteItem : vote.getItems()) {
                voteItemDtoList.add(new VoteItemDto(voteItem.getId(),
                        voteItem.getItemName(), voteItem.getValue()));
            }
        }

        // AnnouncementDetailDto를 생성하여 반환
        return new AnnouncementDetailDto(
                announcement.getMember().getBan().getName(),
                announcement.getPost(),
                vote != null ? vote.getId() : null, // Vote가 없으면 null
                voteItemDtoList, // VoteItemDto 리스트는 비어있을 수 있음
                announcementCommentDto // CommentDto 리스트는 비어있을 수 있음
        );

    }


    //공지사항 수정하기
    @Transactional
    public void updateAnnouncement(Long announcementId, AnnouncementCreateDto announcementCreateDto) {

         Announcement announcement = announcementRepository.findById(announcementId)
                 .orElseThrow(() -> new RuntimeException("해당 공지사항을 수정할 수 없습니다."));

         announcement.setPost(announcementCreateDto.getPost());
         announcementRepository.save(announcement);
    }


    //공지사항 삭제하기
    @Transactional
    public void deleteAnnouncement(Long announcementId) {
         announcementRepository.deleteById(announcementId);
    }

    /**
     * 공지사항 임시저장 CRUD
     */
    //임시저장하기
    @Transactional
    public void createAnnouncementStorage(Long memberId, AnnouncementCreateDto announcementCreateDto){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 멤버를 찾을 수 없습니다."));
        Announcement announcement = new Announcement(announcementCreateDto.getPost(), member);
        announcement.setStored(true);
        announcementRepository.save(announcement);
    }

    //임시저장 목록 불러오기
    public List<AnnouncementStorageListDto> getAnnouncementStorage(Long memberId){


        List<Announcement> announcementList = announcementRepository.findAllByStoredTrueAndMember_Id(memberId);

        List<AnnouncementStorageListDto> announcementStorageList = new ArrayList<>();

        for(Announcement announcement : announcementList) {
            announcementStorageList.add(new AnnouncementStorageListDto(announcement.getPost().getTitle(),
                    announcement.getPost().getCreatedDateTime().toLocalDate(),announcement.getId()));
        }

        return announcementStorageList;
    }

    //불러온 임시저장 선택하기
    public AnnouncementCreateDto getAnnouncementStoredDetail(Long announcementId){
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("해당 임시저장 공지사항을 찾을 수 없습니다"));

        return new AnnouncementCreateDto(announcement.getPost());
    }
}

