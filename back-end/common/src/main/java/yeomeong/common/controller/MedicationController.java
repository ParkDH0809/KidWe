package yeomeong.common.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.service.MedicationService;


@RestController
@RequestMapping("/medications")
@RequiredArgsConstructor
public class MedicationController {


    private final MedicationService medicationService;


//    @Description("반 별 특정월 투약의뢰서 조회하기")
//    @GetMapping("/{ban_id}/{year}/{month}")
//    public ResponseEntity<List<MedicationByKidAndMonthDto>> getMedicationByBanAndMonth(
//            @PathVariable("ban_id") Long banId,
//            @PathVariable("year") int year,
//            @PathVariable("month") int mMonth){
//
//        List<MedicationByKidAndMonthDto> medications
//
//        return ResponseEntity.ok(medications);
//    }


}
