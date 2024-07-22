package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.medication.MedicationByKidAndMonthDto;
import yeomeong.common.entity.medication.Medication;
import yeomeong.common.repository.MedicationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;


//    public List<MedicationByKidAndMonthDto> getMedicationsByBanAndMonth(Long banId, String yearMonth) {
//    }

}
