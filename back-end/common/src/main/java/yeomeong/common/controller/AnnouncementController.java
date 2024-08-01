package yeomeong.common.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.post.announcement.*;
import yeomeong.common.service.AnnouncementService;
import yeomeong.common.service.VoteService;

import java.util.List;

@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
@Tag(name = "공지사항 ")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final VoteService voteService;

    //유치원 공지사항 생성하기
    @PostMapping("")
    public ResponseEntity<Void> createAnnouncement(
            Long memberId,
            @RequestBody AnnouncementCreateDto announcementCreateDto){
        announcementService.createAnnouncementByKindergarten(memberId,announcementCreateDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //유치원 공지사항 조회하기 ( {선생님,학부모} , 원장님 따로 )
    @GetMapping("/list/{member_id}")
    public ResponseEntity<List<AnnouncementListDto>> getAnnouncementList(
            @PathVariable("member_id") Long memberId){

        List<AnnouncementListDto> announcementListDtos = announcementService.getAnnouncementList(memberId);
        return ResponseEntity.ok(announcementListDtos);
    }


    //유치원 공지사항 상세보기
    @GetMapping("/list/{announcement_id}")
    public ResponseEntity<AnnouncementDetailDto> getAnnouncementDetail(
            @PathVariable("announcement_id") Long announcementId){

        AnnouncementDetailDto announcementDetailDto = announcementService.getAnnouncementDetail(announcementId);

        return ResponseEntity.ok(announcementDetailDto);
    }

    //유치원 공지사항 수정하기
    @PutMapping("/{announcement_id}")
    public ResponseEntity<AnnouncementCreateDto> updateAnnouncement(
            @PathVariable("announcement_id") Long announcementId,
            @RequestBody AnnouncementCreateDto announcementCreateDto){

        announcementService.updateAnnouncement(announcementId, announcementCreateDto);

        return ResponseEntity.ok(announcementCreateDto);
    }

    //유치원 공지사항 삭제하기
    @DeleteMapping("/{announcement_id}")
    public ResponseEntity<Void> deleteAnnouncement(
            @PathVariable("announcement_id") Long announcementId){
        announcementService.deleteAnnouncement(announcementId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //공지사항에 투표 생성하기
    @PostMapping("/{announcement_id}/vote")
    public ResponseEntity<VoteCreateDto> createVote(
            @PathVariable ("announcement_id") Long announcement_id,
            @RequestBody VoteCreateDto voteCreateDto){

        voteService.createVote(announcement_id, voteCreateDto);

        return ResponseEntity.ok().build();
    }

    //투표하기
    @PostMapping("/{vote_id}")
    public ResponseEntity<VoteResultDto> addVote(
            @PathVariable("vote_id") Long voteId,
             int index){

        VoteResultDto result = voteService.doVote(voteId, index);

        return ResponseEntity.ok(result);
    }

    //임시저장 목록 불러오기
    @GetMapping("/{member_id}")
    public ResponseEntity<List<AnnouncementStorageListDto>> getAnnouncementStorageList(
            @PathVariable("member_id") Long memberId){

       return ResponseEntity.ok(announcementService.getAnnouncementStorage(memberId));

    }

    //임시저장 게시글 불러오기
    @GetMapping("/{announcement_id}")
    public ResponseEntity<AnnouncementCreateDto> getAnnouncementisStored(
            @PathVariable("announcement_id") Long announcementId){

        return ResponseEntity.ok(announcementService.getAnnouncementStoredDetail(announcementId));
    }

    //임시저장 생성하기
    @PostMapping("/{member_id}")
    public ResponseEntity<Void> createAnnouncementStored(
            @PathVariable("member_id") Long memberId,
            @RequestBody AnnouncementCreateDto announcementCreateDto){

        announcementService.createAnnouncementStorage(memberId,announcementCreateDto);

        return ResponseEntity.ok().build();
    }

}
