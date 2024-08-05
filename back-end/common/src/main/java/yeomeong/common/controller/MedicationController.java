package yeomeong.common.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Tag(name = "투약의뢰서 API", description = "투약의뢰서 조회, 생성, 삭제 API")
public class MedicationController {


    private final MedicationService medicationService;


    @GetMapping("ban/{ban_id}/{year}/{month}")
    @Operation(summary = "반 별 특정 월의 투약의뢰서를 조회합니다(원장, 선생)", description = "반 id와 연월 정보를 통해 투약의뢰서 목록을 조회합니다.")
    public ResponseEntity<List<MedicationByKidAndMonthDto>> getMedicationByBanAndMonth(
            @PathVariable("ban_id") Long banId,
            @PathVariable("year") int year,
            @PathVariable("month") int month) {


        List<MedicationByKidAndMonthDto> medications = medicationService.getMedicationsByBanAndMonth(banId, year, month);

        return ResponseEntity.ok(medications);
    }


    @Operation(summary = "아이의 특정 월의 투약의뢰서를 조회합니다. (부모)", description = "아이 id, 연, 월을 정보를 통해 투약의뢰서 목록을 조회합니다.")
    @GetMapping("kid/{kid_id}/{year}/{month}")
    public ResponseEntity<List<MedicationByKidDto>> getMedicationByKid(
            @PathVariable("kid_id") Long kidId,
            @PathVariable("year") int year,
            @PathVariable("month") int month){

        List<MedicationByKidDto> medications = medicationService.getMedicationByKid(kidId,year,month);

        return ResponseEntity.ok(medications);
    }

    @GetMapping("/{medication_id}")
    @Operation(summary = "해당 투약의뢰서를 상세조회합니다.", description = "투약의뢰서 id를 통해 투약의뢰서를 상세조회합니다.")
    public ResponseEntity<MedicationDetailDto> getMedicationDetail(
            @PathVariable("medication_id") Long medicationId){

        MedicationDetailDto medicationDetailDto = medicationService.getMedicationDetail(medicationId);
        return ResponseEntity.ok(medicationDetailDto);
    }


    @PostMapping("/{kid_id}")
    @Operation(summary = "투약의뢰서를 작성합니다", description = "투약의뢰서 내용과 아이 id를 통해 투약의뢰서를 작성합니다.")
    public ResponseEntity<MedicationCreateDto> createMedication(
            @RequestBody MedicationCreateDto medicationCreateDto,
            @PathVariable("kid_id") Long kidId){


        medicationCreateDto.setKidId(kidId);

        return ResponseEntity.ok(medicationService.createMedication(medicationCreateDto,kidId));
    }

    @DeleteMapping("/{medication_id}")
    @Operation(summary = "투약의뢰서 삭제합니다.", description = "해당 투약의뢰서의 id를 통해 투약의뢰서를 삭제합니다.")
    public ResponseEntity<Void> deleteMedicationRequest(
            @PathVariable("medication_id") Long medicationId){

        medicationService.removeMedication(medicationId);

        return ResponseEntity.noContent().build();
    }

}
