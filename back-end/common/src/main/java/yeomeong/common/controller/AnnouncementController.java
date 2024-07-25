package yeomeong.common.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.post.announcement.AnnouncementCreateDto;
import yeomeong.common.dto.post.announcement.AnnouncementDetailDto;
import yeomeong.common.dto.post.announcement.AnnouncementListDto;
import yeomeong.common.service.AnnouncementService;

import java.util.List;

@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
@Tag(name = "공지사항 ")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    //유치원 공지사항 생성하기
    @PostMapping("")
    public ResponseEntity<Void> createAnnouncement(
            Long memberId,
            @RequestBody AnnouncementCreateDto announcementCreateDto){
        announcementService.createAnnouncementByKindergarten(memberId,announcementCreateDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //유치원 공지사항 조회하기 ( {선생님,학부모} , 원장님 따로 )
//    @GetMapping("/list/{member_id}")
//    public ResponseEntity<List<AnnouncementListDto>> getAnnouncementList(
//            @PathVariable("member_id") Long memberId){
//
//        List<AnnouncementListDto> announcementListDtos = announcementService.getAnnouncementList(memberId);
//        return ResponseEntity.ok(announcementListDtos);
//    }


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

}
