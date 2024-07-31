package yeomeong.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.kid.KidJoinRequestDto;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KidReposiory;
import yeomeong.common.repository.KindergartenRepository;

@Service
@Slf4j
public class KidService {

    final KidReposiory kidReposiory;
    final KindergartenRepository kindergartenRepository;
    final BanRepository banRepository;

    public KidService(KidReposiory kidReposiory, KindergartenRepository kindergartenRepository,
        BanRepository banRepository) {
        this.kidReposiory = kidReposiory;
        this.kindergartenRepository = kindergartenRepository;
        this.banRepository = banRepository;
    }

    public void joinKid(KidJoinRequestDto kidJoinRequestDto) {
        log.info(kidJoinRequestDto.toString());
        kidReposiory.save(
            KidJoinRequestDto.toKidEntity(
                kidJoinRequestDto,
                kindergartenRepository.findById(kidJoinRequestDto.getKindergartenId())
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)),
                banRepository.findById(kidJoinRequestDto.getBanId())
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)))
        );
    }
}
