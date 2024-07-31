package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.medication.MedicationByKidAndMonthDto;
import yeomeong.common.dto.medication.MedicationByKidDto;
import yeomeong.common.dto.medication.MedicationCreateDto;
import yeomeong.common.dto.medication.MedicationDetailDto;
import yeomeong.common.repository.MedicationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicationService {

    private final MedicationRepository medicationRepository;


    //반 별 월 별 투약의뢰서 가져오기 (   )
    public List<MedicationByKidAndMonthDto> getMedicationsByBanAndMonth(Long banId, int year, int month) {


        return medicationRepository.medicationByKidAndMonthDtoList(banId,year,month);
    }
    // 아이별 투약 의뢰서 가져오기
    public List<MedicationByKidDto> getMedicationByKid(Long kidId, int year, int month){
        return medicationRepository.medicationByKidDtoList(kidId,month,year);
    }

    //투약의뢰서 상세보기
    public MedicationDetailDto getMedicationDetail(Long medicationId){

        return medicationRepository.getMedicationDetail(medicationId);
    }

    //투약의뢰서 생성하기
    @Transactional
    public void createMedication(MedicationCreateDto medicationCreateDto, Long kidId){

        medicationRepository.createMedication(medicationCreateDto, kidId);
    }

    //투약의뢰서 제거하기
    public void removeMedication(Long medicationId){
        medicationRepository.removeMedication(medicationId);
    }

}

