package yeomeong.common.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.kid.KidDetailInfoDto;
import yeomeong.common.dto.kid.KidJoinRequestDto;
import yeomeong.common.dto.kid.KidUpdateInfoDto;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.KindergartenRepository;

@Service
@Slf4j
public class KidService {

    final KidRepository kidRepository;
    final KindergartenRepository kindergartenRepository;
    final BanRepository banRepository;

    public KidService(KidRepository kidRepository, KindergartenRepository kindergartenRepository, BanRepository banRepository) {
        this.kidRepository = kidRepository;
        this.kindergartenRepository = kindergartenRepository;
        this.banRepository = banRepository;
    }

    public void joinKid(KidJoinRequestDto kidJoinRequestDto) {
        log.info(kidJoinRequestDto.toString());
        kidRepository.save(KidJoinRequestDto.toKidEntity(kidJoinRequestDto,
            kindergartenRepository.findById(kidJoinRequestDto.getKindergartenId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)),
            banRepository.findById(kidJoinRequestDto.getBanId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)))
        );
    }

    public KidDetailInfoDto getKidInfo(Long kidId) {
        return KidDetailInfoDto.toKidDetailInfoDto(
            kidRepository.findByIdAndIsDeletedFalse(kidId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_KID)));
    }

    public void updateKidInfo(KidUpdateInfoDto kidUpdateInfoDto) {
        Kid kid = kidRepository.findByIdAndIsDeletedFalse(kidUpdateInfoDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_KID));
        kid.updateFromDto(kidUpdateInfoDto);
        if (kidUpdateInfoDto.hasBanId()) {
            kid.setNewBan(banRepository.findById(kidUpdateInfoDto.getBanId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_BAN_ID)));
        }
        kidRepository.save(kid);
    }

    public void deleteKidInfo(Long kidId) {
        try {
            kidRepository.deleteKidById(kidId);
        } catch(EntityNotFoundException e) {
            throw new CustomException(ErrorCode.INVALID_ID);
        }
    }

}