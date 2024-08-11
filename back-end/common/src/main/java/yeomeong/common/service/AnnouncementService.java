package yeomeong.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yeomeong.common.dto.post.announcement.*;

import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.entity.post.Announcement;
import yeomeong.common.entity.post.AnnouncementImage;
import yeomeong.common.entity.post.Vote;
import yeomeong.common.entity.post.VoteItem;
import yeomeong.common.entity.post.comment.AnnouncementComment;
import yeomeong.common.repository.AnnouncementImageRepository;
import yeomeong.common.repository.AnnouncementRepository;
import yeomeong.common.repository.MemberRepository;
import yeomeong.common.util.FileUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final MemberRepository memberRepository;
    private final AnnouncementImageRepository announcementImageRepository;
    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    /**
     * 유치원별 공지사항 생성하기 (원장님)
     * 반 별 공지사항 생성하기 (선생님)
     */
    @Transactional
    public void createAnnouncementByKindergarten(Long memberId, AnnouncementCreateDto announcementCreateDto, List<MultipartFile> images) throws Exception {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("해당 맴버가 없어용"));


        Announcement announcement =new Announcement(announcementCreateDto.getPost(),
                member,
                LocalDateTime.now());


        announcement.setStored(false);

        if(images != null ){
            for( MultipartFile image : images){

                ObjectMetadata metadata =new ObjectMetadata();

                metadata.setContentLength(image.getSize());
                metadata.setContentType(image.getContentType());


                String fileName = FileUtil.convertFileName(image);

                try {
                    s3Client.putObject(new PutObjectRequest(bucketName,fileName, image.getInputStream(),metadata));

                }
                catch (Exception e){
                   e.printStackTrace();
                }
                AnnouncementImage announcementImage =new AnnouncementImage(
                        s3Client.getUrl(bucketName, fileName).toString(),
                        announcement
                );
                announcementImageRepository.save(announcementImage);
                announcement.getAnnouncementImages().add(announcementImage);

            }
        }

        announcementRepository.save(announcement);
    }

     /** 유치원별 공지사항 조회하기
     * 반별 공지사항 조회하기
     * 공지사항 조회하기
     */
     public List<AnnouncementListDto> getAnnouncementList(Long memberId){
         Member member = memberRepository.findById(memberId)
                 .orElseThrow(() -> new RuntimeException("해당 멤버를 찾을 수 없습니다."));

         List<AnnouncementListDto> announcementByAll = announcementRepository.getAnnouncementByAll(member.getKindergarten().getId());
         List<AnnouncementListDto> announcementDtoList = new ArrayList<>(announcementByAll);
         //전체 공지사항 가져오기

         if(member.getRole() == rtype.ROLE_DIRECTOR){ //원장님일 때 해당 유치원 공지사항 모두 가져오기

             List<AnnouncementListDto> announcementByAllBan = announcementRepository.getAnnouncementByAllBan(member.getKindergarten().getId());
             announcementDtoList.addAll(announcementByAllBan);

         } //해당 유치원 전체 반에 대한 공지사항 가져오기
         else {
             List<AnnouncementListDto> announcementByBan = announcementRepository.getAnnouncementByAllBan(member.getBan().getId());
             announcementDtoList.addAll(announcementByBan);

         } //선생님이나 학부모일 땐 해당 반 + 전체 공지 다 가져오기

         for(AnnouncementListDto announcementOne : announcementDtoList){
             if(announcementOne.getMemberBan() == null){
                 announcementOne.setMemberBan("전체 공지");
             }
         }
         if(announcementDtoList.size() > 1)
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

        List<AnnouncementImage> announcementImages = announcement.getAnnouncementImages();

        List<AnnouncementImageDto> images = new ArrayList<>();

        if(announcementImages != null) {
            for (AnnouncementImage image : announcementImages) {
                images.add(new AnnouncementImageDto(image.getImageUrl()));
            }
        }

        // AnnouncementDetailDto를 생성하여 반환
        return new AnnouncementDetailDto(
                announcement.getMember().getBan() != null? announcement.getMember().getBan().getName() : "전체 공지",
                announcement.getPost(),
                !images.isEmpty() ? images : null,
                vote != null ? vote.getId() : null, // Vote가 없으면 null
                voteItemDtoList, // VoteItemDto 리스트는 비어있을 수 있음
                announcement.getCommentList().size(),
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
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("해당 공지사항이 없으세요"));

        announcement.setDeleted(true);

    }

    /**
     * 공지사항 임시저장 CRUD
     */
    //임시저장하기
    @Transactional
    public void createAnnouncementStorage(Long memberId, AnnouncementCreateDto announcementCreateDto){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 멤버를 찾을 수 없습니다."));
        Announcement announcement = new Announcement(announcementCreateDto.getPost(), member,LocalDateTime.now());
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

