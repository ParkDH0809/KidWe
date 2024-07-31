package yeomeong.common.service;

import org.springframework.stereotype.Service;
import yeomeong.common.dto.kindergarten.KindergartenSaveRequestDto;
import yeomeong.common.repository.KindergartenRepository;

@Service
public class KindergartenService {

    final KindergartenRepository kindergartenRepository;

    public KindergartenService(KindergartenRepository kindergartenRepository) {
        this.kindergartenRepository = kindergartenRepository;
    }

    public void createKindergarten(KindergartenSaveRequestDto kindergartenSaveRequestDto) {
        kindergartenRepository.save(KindergartenSaveRequestDto.toKindergartenEntity(kindergartenSaveRequestDto));
    }

}
