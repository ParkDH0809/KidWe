package yeomeong.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.kid.KidCreateRequestDto;
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

    public void createKid(KidCreateRequestDto kidCreateRequestDto) {
        log.info(kidCreateRequestDto.toString());
        kidReposiory.save(
            KidCreateRequestDto.toKidEntity(
                kidCreateRequestDto,
                kindergartenRepository.findById(kidCreateRequestDto.getKindergartenId())
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)),
                banRepository.findById(kidCreateRequestDto.getBanId())
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)))
        );
    }
}
