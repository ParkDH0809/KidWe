package yeomeong.common.service;

import org.springframework.stereotype.Service;
import yeomeong.common.dto.ban.BanCreateRequestDto;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KindergartenRepository;

@Service
public class BanService {

    final BanRepository banRepository;
    final KindergartenRepository kindergartenRepository;

    public BanService(BanRepository banRepository, KindergartenRepository kindergartenRepository) {
        this.banRepository = banRepository;
        this.kindergartenRepository = kindergartenRepository;
    }

    public void createBan(BanCreateRequestDto banCreateRequestDto) {
        banRepository.save(BanCreateRequestDto.toBanEntity(
            kindergartenRepository.findById(banCreateRequestDto.getKindergartenId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID)),
            banCreateRequestDto));
    }
}
