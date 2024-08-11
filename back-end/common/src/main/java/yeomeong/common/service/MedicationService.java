package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yeomeong.common.dto.medication.MedicationByKidAndMonthDto;
import yeomeong.common.dto.medication.MedicationByKidDto;
import yeomeong.common.dto.medication.MedicationCreateDto;
import yeomeong.common.dto.medication.MedicationDetailDto;
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


        return medicationRepository.medicationByKidAndMonthDtoList(banId,year,month);
    }
    // 아이별 투약 의뢰서 가져오기
    public List<MedicationByKidDto> getMedicationByKid(Long kidId, int year, int month){

        return medicationRepository.medicationByKidDtoList(kidId,year,month);
    }

    //투약의뢰서 상세보기
    public MedicationDetailDto getMedicationDetail(Long medicationId){

        return medicationRepository.getMedicationDetail(medicationId);
    }

    //투약의뢰서 생성하기
    @Transactional
    public MedicationCreateDto createMedication(MultipartFile medicineImage, MultipartFile signImage, MedicationCreateDto medicationCreateDto, Long kidId, Long memberId) throws Exception {


        return medicationRepository.createMedication(medicineImage,signImage,medicationCreateDto, kidId, memberId);
    }

    //투약의뢰서 제거하기
    @Transactional
    public void removeMedication(Long medicationId){
        medicationRepository.removeMedication(medicationId);
    }

}

