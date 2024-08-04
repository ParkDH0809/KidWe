package yeomeong.common.service;

import org.springframework.stereotype.Service;
import yeomeong.common.dto.ban.BanCreateRequestDto;
import yeomeong.common.dto.ban.BanDetailInfoDto;
import yeomeong.common.dto.ban.BanNameChangeRequestDto;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.KindergartenRepository;

@Service
public class BanService {

    final BanRepository banRepository;
    final KindergartenRepository kindergartenRepository;

    public BanService(BanRepository banRepository, KindergartenRepository kindergartenRepository, KidRepository kidRepository) {
        this.banRepository = banRepository;
        this.kindergartenRepository = kindergartenRepository;
    }

    public void createBan(BanCreateRequestDto banCreateRequestDto) {
        banRepository.save(BanCreateRequestDto.toBanEntity(
            kindergartenRepository.findById(banCreateRequestDto.getKindergartenId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID)),
            banCreateRequestDto));
    }

    public BanDetailInfoDto getBanInfo(Long banId) {
        return BanDetailInfoDto.toBanDetailInfoDto(
            banRepository.findById(banId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID)));
    }

    public void changeBanName(BanNameChangeRequestDto banNameChangeRequestDto) {
        if (banRepository.changeBanName(banNameChangeRequestDto.getId(), banNameChangeRequestDto.getName()) != 1) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

}
