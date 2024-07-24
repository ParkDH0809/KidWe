package yeomeong.common.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.medication.MedicationByKidAndMonthDto;
import yeomeong.common.dto.medication.MedicationByKidDto;
import yeomeong.common.dto.medication.MedicationCreateDto;
import yeomeong.common.dto.medication.MedicationDetailDto;
import yeomeong.common.service.MedicationService;

import java.util.List;


@RestController
@RequestMapping("/medications")
@RequiredArgsConstructor
@Tag(name = "투약의뢰서", description = "투약의뢰서 조회, 생성, 삭제 API")
public class MedicationController {


    private final MedicationService medicationService;


    @Description("반 별 특정월 투약의뢰서 조회하기")
    @GetMapping("/{ban_id}/{year}/{month}")
    public ResponseEntity<List<MedicationByKidAndMonthDto>> getMedicationByBanAndMonth(
            @PathVariable("ban_id") Long banId,
            @PathVariable("year") int year,
            @PathVariable("month") int month) {

        List<MedicationByKidAndMonthDto> medications = medicationService.getMedicationsByBanAndMonth(banId, year, month);

        return ResponseEntity.ok(medications);
    }

    @Description("아이별 투약의뢰서 조회하기")
    @GetMapping("/{kid_id}/{year}/{month}")
    public ResponseEntity<List<MedicationByKidDto>> getMedicationByKid(
            @PathVariable("kid_id") Long kidId,
            @PathVariable("year") int year,
            @PathVariable("month") int month){

        List<MedicationByKidDto> medications = medicationService.getMedicationByKid(kidId,year,month);

        return ResponseEntity.ok(medications);
    }

    @Description("투약의뢰서 상세 조회하기")
    @GetMapping("/{medication_id}")
    public ResponseEntity<MedicationDetailDto> getMedicationDetail(
            @PathVariable("medication_id") Long medicationId){

        MedicationDetailDto medicationDetailDto = medicationService.getMedicationDetail(medicationId);
        return ResponseEntity.ok(medicationDetailDto);
    }


    @Description("투약의뢰서 생성하기")
    @PostMapping("/{kid_id}")
    public ResponseEntity<MedicationCreateDto> createMedication(
            @RequestBody MedicationCreateDto medicationCreateDto,
            @PathVariable("kid_id") Long kidId){

        medicationService.createMedication(medicationCreateDto,kidId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(medicationCreateDto);
    }

    @Description("투약의뢰서 삭제하기")
    @DeleteMapping("/{medication_id}")
    public ResponseEntity<Void> deleteMedicationRequest(
            @PathVariable("medication_id") Long medicationId){

        medicationService.removeMedication(medicationId);

        return ResponseEntity.noContent().build();
    }


}
