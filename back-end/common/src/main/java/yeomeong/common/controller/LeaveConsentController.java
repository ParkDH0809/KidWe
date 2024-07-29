package yeomeong.common.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.leaveconsent.LeaveConsentByMonthAndBanListDto;
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

    //반별 특정월 리스트 조회하기
//    @GetMapping("/{member_id}/{year}/{month}")
//    public ResponseEntity<List<LeaveConsentByMonthAndBanListDto>> getLeaveConsentByBanAndMonthList(
//            @PathVariable("member_id") Long memberId,
//            Long banId,
//            @PathVariable("year")int year,
//            @PathVariable("month") int month){
//
//
//
////        return ResponseEntity.ok(leaveConsentService.getLeaveConsentByMonthAndBanList(memberId, banId,year, month));
//    }

    //아이별 특정월 귀가동의서



}
