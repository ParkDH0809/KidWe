package yeomeong.common.controller;


import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "공지사항 API ", description = "공지사항 관련 API")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final VoteService voteService;

    //유치원 공지사항 생성하기
    @PostMapping("/{memberId}")
    @Operation(summary = "유치원 공지사항을 생성합니다." ,description = "memberId를 활용하여 공지사항을 작성합니다.")
    public ResponseEntity<Void> createAnnouncement(
            @PathVariable("memberId") Long memberId,
            @RequestBody AnnouncementCreateDto announcementCreateDto){
        announcementService.createAnnouncementByKindergarten(memberId,announcementCreateDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //유치원 공지사항 조회하기 ( {선생님,학부모} , 원장님 따로 )
    @GetMapping("/list/{memberId}")
    @Operation(summary = "유치원 공지사항을 조회합니다" , description = "memberId를 통한 회원의 유형에 따른 공지사항을 조회합니다.")
    public ResponseEntity<List<AnnouncementListDto>> getAnnouncementList(
            @PathVariable("memberId") Long memberId){

        List<AnnouncementListDto> announcementListDtos = announcementService.getAnnouncementList(memberId);
        return ResponseEntity.ok(announcementListDtos);
    }


    //유치원 공지사항 상세보기
    @GetMapping("/detail/{announcementId}")
    @Operation(summary = "유치원의 공지사항을 상세보기합니다" , description = "공지사항 id를 활용하여 공지사항을 상세 조회합니다.")
    public ResponseEntity<AnnouncementDetailDto> getAnnouncementDetail(
            @PathVariable("announcementId") Long announcementId){

        AnnouncementDetailDto announcementDetailDto = announcementService.getAnnouncementDetail(announcementId);

        return ResponseEntity.ok(announcementDetailDto);
    }

    //유치원 공지사항 수정하기
    @PutMapping("/{announcementId}")
    @Operation(summary = "유치원 공지사항을 수정합니다", description = "유치원 id와 수정내용을 요청받아 수정합니다.")
    public ResponseEntity<AnnouncementCreateDto> updateAnnouncement(
            @PathVariable("announcementId") Long announcementId,
            @RequestBody AnnouncementCreateDto announcementCreateDto){

        announcementService.updateAnnouncement(announcementId, announcementCreateDto);

        return ResponseEntity.ok(announcementCreateDto);
    }

    //유치원 공지사항 삭제하기
    @DeleteMapping("/{announcementId}")
    @Operation(summary = "공지사항을 삭제합니다", description = "해당 공지사항 id를 받아온 뒤 이를 삭제합니다.")
    public ResponseEntity<Void> deleteAnnouncement(
            @PathVariable("announcementId") Long announcementId){
        announcementService.deleteAnnouncement(announcementId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //공지사항에 투표 생성하기
    @PostMapping("/vote/{announcementId}")
    @Operation(summary = "공지사항 투표를 생성합니다.", description = "공지사항 id와 투표형식을 받아옵니다. 공지사항이 먼저 작성된 후 투표를 작성할 수 있습니다.")
    public ResponseEntity<VoteCreateDto> createVote(
            @PathVariable ("announcementId") Long announcement_id,
            @RequestBody VoteCreateDto voteCreateDto){

        voteService.createVote(announcement_id, voteCreateDto);

        return ResponseEntity.ok().build();
    }

    //투표하기
    @PostMapping("/vote/items/{voteId}/{voteItemId}")
    @Operation(summary = "원하는 목록에 투표합니다." , description = "원하는 곳에 투표합니다.")
    public ResponseEntity<VoteResultDto> addVote(
            @PathVariable("voteId") Long voteId,
            @PathVariable("voteItemId")Long voteItemId){

        VoteResultDto result = voteService.doVote(voteId, voteItemId);

        return ResponseEntity.ok(result);
    }

    //임시저장 목록 불러오기
    @GetMapping("/storage/list/{memberId}")
    @Operation(summary = "해당 맴버가 임시저장한 공지사항 목록을 불러옵니다.", description = "해당 멤버의 id를 받아와 이를 활용하여 임시저장 했던 공지사항 목록들을 불러옵니다.")
    public ResponseEntity<List<AnnouncementStorageListDto>> getAnnouncementStorageList(
            @PathVariable("memberId") Long memberId){

       return ResponseEntity.ok(announcementService.getAnnouncementStorage(memberId));

    }

    //임시저장 게시글 불러오기
    @GetMapping("/storage/{announcementId}")
    @Operation(summary = "임시저장 게시글을 상세 불러오기합니다.", description = "임시저장했던 글을 불러옵니다.")
    public ResponseEntity<AnnouncementCreateDto> getAnnouncementisStored(
            @PathVariable("announcementId") Long announcementId){

        return ResponseEntity.ok(announcementService.getAnnouncementStoredDetail(announcementId));
    }

    //임시저장 생성하기
    @PostMapping("/storage/{memberId}")
    @Operation(summary = "임시저장을 생성합니다.", description = "해당 맴버 id를 받아온 뒤 작성한 내용을 임시저장합니다.")
    public ResponseEntity<Void> createAnnouncementStored(
            @PathVariable("memberId") Long memberId,
            @RequestBody AnnouncementCreateDto announcementCreateDto){

        announcementService.createAnnouncementStorage(memberId,announcementCreateDto);

        return ResponseEntity.ok().build();
    }

}
