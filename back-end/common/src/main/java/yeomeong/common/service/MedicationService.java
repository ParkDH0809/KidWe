package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.repository.jpa.MedicationRepository;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;


//    public List<MedicationByKidAndMonthDto> getMedicationsByBanAndMonth(Long banId, String yearMonth) {
//    }

}
