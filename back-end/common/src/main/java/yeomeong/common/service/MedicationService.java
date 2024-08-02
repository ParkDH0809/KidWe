package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.medication.MedicationByKidAndMonthDto;
import yeomeong.common.dto.medication.MedicationByKidDto;
import yeomeong.common.dto.medication.MedicationCreateDto;
import yeomeong.common.dto.medication.MedicationDetailDto;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.MedicationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final KidRepository kidRepository;
    private final BanRepository banRepository;


    //반 별 월 별 투약의뢰서 가져오기 (   )
    public List<MedicationByKidAndMonthDto> getMedicationsByBanAndMonth(Long banId, int year, int month) {

        Ban ban = banRepository.findById(banId).orElseThrow(() -> new RuntimeException("해당 반을 찾을 수 없습니다"));

        return medicationRepository.medicationByKidAndMonthDtoList(banId,year,month);
    }
    // 아이별 투약 의뢰서 가져오기
    public List<MedicationByKidDto> getMedicationByKid(Long kidId, int year, int month){

        Kid kid = kidRepository.findById(kidId).orElseThrow(() -> new RuntimeException("해당 아이를 찾을 수 없습니다."));

        return medicationRepository.medicationByKidDtoList(kidId,year,month);
    }

    //투약의뢰서 상세보기
    public MedicationDetailDto getMedicationDetail(Long medicationId){

        return medicationRepository.getMedicationDetail(medicationId);
    }

    //투약의뢰서 생성하기
    @Transactional
    public MedicationCreateDto createMedication(MedicationCreateDto medicationCreateDto, Long kidId){

        return medicationRepository.createMedication(medicationCreateDto, kidId);
    }

    //투약의뢰서 제거하기
    public void removeMedication(Long medicationId){
        medicationRepository.removeMedication(medicationId);
    }

}

