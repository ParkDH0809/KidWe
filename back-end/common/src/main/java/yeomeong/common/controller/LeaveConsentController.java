package yeomeong.common.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yeomeong.common.dto.leaveconsent.LeaveConsentByMonthAndBanListDto;
import yeomeong.common.dto.leaveconsent.LeaveConsentCreateDto;
import yeomeong.common.dto.leaveconsent.LeaveConsentDetailDto;
import yeomeong.common.service.LeaveConsentService;

import java.util.List;

@RestController
@RequestMapping("/leaveconsents")
@RequiredArgsConstructor
@Tag(name = "귀가동의서 API", description = "귀가동의서 관련 API")
public class LeaveConsentController {

    private final LeaveConsentService leaveConsentService;

    /**
     * 반별 특정월 귀가 동의서
     */

    //반별 특정월 리스트 조회하기(학부모, 원장님)
    @GetMapping("/ban/{banId}/{year}/{month}")
    @Operation(summary = "반 별 특정월에 귀가동의서를 조회합니다.", description = "반 id, 연, 월을 통해 해당 월에 귀가동의서를 조회합니다.")
    public ResponseEntity<List<LeaveConsentByMonthAndBanListDto>> getLeaveConsentByBanAndMonthList(
            @PathVariable("banId") Long banId,
            @PathVariable("year") int year,
            @PathVariable("month") int month) {


        return ResponseEntity.ok(leaveConsentService.getLeaveConsentByMonthAndBanList(banId, year, month));
    }

    //자기 아이 특정월 리스트 조회하기( 학부모 )
    @GetMapping("/kid/{kidId}/{year}/{month}")
    @Operation(summary = "학부모 자녀 특정월 귀가동의서를 조회합니다.", description = "아이 id, 연, 월을 통해 해당 귀가동의서 목록을 조회합니다.")
    public ResponseEntity<List<LeaveConsentByMonthAndBanListDto>> getLeaveConsentByKidAndMonthList(
            @PathVariable("kidId") Long kidId,
            @PathVariable("year") int year,
            @PathVariable("month") int month) {

            return ResponseEntity.ok(leaveConsentService.getLeaveConsentByKid(kidId,year,month));
    }


    //귀가동의서 작성하기
    @PostMapping(value = "/{kidId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "귀가동의서를 생성합니다", description = "kidId, 귀가동의서에 작성한 내용을 통해 귀가동의서를 생성합니다.")
    public ResponseEntity<Void> createLeaveConsent(
            @PathVariable("kidId") Long kidId,
            Long memberId,
            @RequestPart("dto") LeaveConsentCreateDto leaveConsentCreateDto,
            @RequestPart("sign") MultipartFile file) throws Exception {

        leaveConsentService.createLeaveConsent(kidId,memberId, leaveConsentCreateDto, file);


        return ResponseEntity.ok().build();
    }


    //귀가동의서 삭제하기
    @DeleteMapping("/{leaveconsentId}")
    @Operation(summary = "귀가동의서를 삭제합니다", description = "해당 귀가동의서 id로 귀가동의서를 삭제합니다.")
    public ResponseEntity<Void> removeLeaveConsent(
            @PathVariable("leaveconsentId") Long leaveConsentId){

        leaveConsentService.removeLeaveConsent(leaveConsentId);

        return ResponseEntity.ok().build();
    }

    //귀가동의서 상세보기
    @GetMapping("/{leaveconsentId}")
    @Operation(summary = "귀가동의서 상세보기", description = "해당 귀가동의서를 상세 조회합니다.")
    public ResponseEntity<LeaveConsentDetailDto> getLeaveConsentDetail(
            @PathVariable("leaveconsentId") Long leaveConsentId,
            Long memberId
    ){

        return ResponseEntity.ok(leaveConsentService.getLeaveConsentDetail(memberId,leaveConsentId));
    }


}
