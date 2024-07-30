package yeomeong.common.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.leaveconsent.LeaveConsentByMonthAndBanListDto;
import yeomeong.common.dto.leaveconsent.LeaveConsentCreateDto;
import yeomeong.common.service.LeaveConsentService;

import java.util.List;

@RestController("/leaveconsents")
@RequiredArgsConstructor
@Tag(name = "귀가동의서 API")
public class LeaveConsentController {

    private final LeaveConsentService leaveConsentService;

    /**
     * 반별 특정월 귀가 동의서
     */

    //반별 특정월 리스트 조회하기(학부모, 원장님)
    @GetMapping("/ban/{ban_id}/{year}/{month}")
    public ResponseEntity<List<LeaveConsentByMonthAndBanListDto>> getLeaveConsentByBanAndMonthList(
            @PathVariable("ban_id") Long banId,
            @PathVariable("year") int year,
            @PathVariable("month") int month) {


        return ResponseEntity.ok(leaveConsentService.getLeaveConsentByMonthAndBanList(banId, year, month));
    }

    //자기 아이 특정월 리스트 조회하기( 학부모 )
    @GetMapping("/kid/{kid_id}/{year}/{month}")
    public ResponseEntity<List<LeaveConsentByMonthAndBanListDto>> getLeaveConsentByKidAndMonthList(
            @PathVariable("kid_id") Long kidId,
            @PathVariable("year") int year,
            @PathVariable("month") int month) {

            return ResponseEntity.ok(leaveConsentService.getLeaveConsentByKid(kidId,year,month));
    }


    //귀가동의서 작성하기
    @PostMapping("/{kid_id}")
    public ResponseEntity<Void> createLeaveConsent(
            @PathVariable("kid_id") Long kidId,
            @RequestBody LeaveConsentCreateDto leaveConsentCreateDto){

        leaveConsentService.createLeaveConsent(kidId, leaveConsentCreateDto);


        return ResponseEntity.ok().build();
    }


    //귀가동의서
    @DeleteMapping("/{leaveconsent_id}")
    public ResponseEntity<Void> removeLeaveConsent(
            @PathVariable("leaveconsent_id") Long leaveConsentId){

        leaveConsentService.removeLeaveConsent(leaveConsentId);

        return ResponseEntity.ok().build();
    }


}
