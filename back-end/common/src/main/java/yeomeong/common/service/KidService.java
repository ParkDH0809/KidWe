package yeomeong.common.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.kid.KidDetailInfoResponseDto;
import yeomeong.common.dto.kid.KidUpdateInfoRequestDto;
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

    public KidDetailInfoResponseDto getKidInfo(Long kidId) {
        return KidDetailInfoResponseDto.toKidDetailInfoDto(
            kidRepository.findByIdAndIsDeletedFalse(kidId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_KID)));
    }

    public void updateKidInfo(KidUpdateInfoRequestDto kidUpdateInfoRequestDto, String picture) {
        Kid kid = kidRepository.findByIdAndIsDeletedFalse(kidUpdateInfoRequestDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_KID));
        kid.updateFromDto(kidUpdateInfoRequestDto, picture);
        if (kidUpdateInfoRequestDto.hasBanId()) {
            kid.setNewBan(banRepository.findById(kidUpdateInfoRequestDto.getBanId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_BAN_ID)));
        }
        if (kidUpdateInfoRequestDto.hasKindergartenId()) {
            kid.setNewKindergarten(kindergartenRepository.findById(kidUpdateInfoRequestDto.getKindergartenId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_BAN_ID)));
        }
        kidRepository.save(kid);
    }

    public void deleteKidInfo(Long kidId) {
        try {
            kidRepository.deleteKidById(kidId);
        } catch(EntityNotFoundException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_ID);
        }
    }

}