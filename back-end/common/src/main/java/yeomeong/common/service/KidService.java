package yeomeong.common.service;

import com.amazonaws.services.s3.AmazonS3;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yeomeong.common.dto.kid.KidDetailInfoResponseDto;
import yeomeong.common.dto.kid.KidUpdateInfoRequestDto;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.KindergartenRepository;
import yeomeong.common.util.FileUtil;

@Service
@Slf4j
public class KidService {

    final KidRepository kidRepository;
    final KindergartenRepository kindergartenRepository;
    final BanRepository banRepository;
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public KidService(KidRepository kidRepository, KindergartenRepository kindergartenRepository, BanRepository banRepository,
        AmazonS3 amazonS3) {
        this.kidRepository = kidRepository;
        this.kindergartenRepository = kindergartenRepository;
        this.banRepository = banRepository;
        this.amazonS3 = amazonS3;
    }

    public KidDetailInfoResponseDto getKidInfo(Long kidId) {
        return KidDetailInfoResponseDto.toKidDetailInfoDto(
            kidRepository.findByIdAndIsDeletedFalse(kidId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_KID)));
    }

    public void updateKidInfo(KidUpdateInfoRequestDto kidUpdateInfoRequestDto, MultipartFile picture) {
        String pictureName = null;
        if (picture != null && !picture.isEmpty()) {
            pictureName = FileUtil.uploadFileToS3(amazonS3, bucketName, picture);
        }
        Kid kid = kidRepository.findByIdAndIsDeletedFalse(kidUpdateInfoRequestDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_KID));
        kid.updateFromDto(kidUpdateInfoRequestDto, pictureName);
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