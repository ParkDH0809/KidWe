package yeomeong.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.kindergarten.KindergartenInfoResponseDto;
import yeomeong.common.dto.kindergarten.KindergartenSaveRequestDto;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.KindergartenRepository;

@Service
@Slf4j
public class KindergartenService {

    final KindergartenRepository kindergartenRepository;

    public KindergartenService(KindergartenRepository kindergartenRepository) {
        this.kindergartenRepository = kindergartenRepository;
    }

    public void createKindergarten(KindergartenSaveRequestDto kindergartenSaveRequestDto) {
        kindergartenRepository.save(KindergartenSaveRequestDto.toKindergartenEntity(kindergartenSaveRequestDto));
    }

    public KindergartenInfoResponseDto getKindergartenInfo(Long kindergartenId) {
        return KindergartenInfoResponseDto.toKindergartenDto(kindergartenRepository.findById(kindergartenId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID)));
    }

}
