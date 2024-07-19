package yeomeong.common.controller;


import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.entity.medication.Medication;
import yeomeong.common.service.MedicationService;

import java.util.List;


@RestController
@RequestMapping("/medications")
@RequiredArgsConstructor
public class MedicationController {


    private final MedicationService medicationService;


    @Description("반 별 특정월 투약의뢰서 조회하기")
    @GetMapping("/{ban_id}/{year-month}")
    public ResponseEntity<List<Medication>> getMedicationByBanAndMonth(
            @PathVariable("ban_id") Long banId,
            @PathVariable("year-month") String yearMonth){

        List<Medication> medications = medicationService.getMedicationsByBanAndMonth(banId, yearMonth);

        return ResponseEntity.ok(medications);
    }


}
