package yeomeong.common.service;

import com.querydsl.core.BooleanBuilder;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.kindergarten.KindergartenInfoResponseDto;
import yeomeong.common.dto.kindergarten.KindergartenSaveRequestDto;
import yeomeong.common.dto.kindergarten.KindergartenSearchDto;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.kindergarten.QKindergarten;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KindergartenRepository;

@Service
@Slf4j
public class KindergartenService {

    private final KindergartenRepository kindergartenRepository;

    public KindergartenService(KindergartenRepository kindergartenRepository, BanRepository banRepository) {
        this.kindergartenRepository = kindergartenRepository;
    }

    public void createKindergarten(KindergartenSaveRequestDto kindergartenSaveRequestDto) {
        kindergartenRepository.save(KindergartenSaveRequestDto.toKindergartenEntity(kindergartenSaveRequestDto));
    }

    public KindergartenInfoResponseDto getKindergartenInfo(Long kindergartenId) {
        return KindergartenInfoResponseDto.toKindergartenDto(kindergartenRepository.findById(kindergartenId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID)));
    }

    public List<KindergartenInfoResponseDto> getSearchedKindergartenInfo(KindergartenSearchDto kindergartenSearchDto) {
        List<KindergartenInfoResponseDto> kindergartenInfoResponseDtos = new ArrayList<>();
        searchKindergartens(kindergartenSearchDto).forEach(kindergartenEntity ->
            kindergartenInfoResponseDtos.add(KindergartenInfoResponseDto.toKindergartenDto(kindergartenEntity)));
        return kindergartenInfoResponseDtos;
    }

    public List<Kindergarten> searchKindergartens(KindergartenSearchDto dto) {
        QKindergarten kindergarten = QKindergarten.kindergarten;
        BooleanBuilder builder = new BooleanBuilder();

        if (dto.getSido() != null && !dto.getSido().isEmpty()) {
            builder.and(kindergarten.address.containsIgnoreCase(dto.getSido()));
        }

        if (dto.getSigungu() != null && !dto.getSigungu().isEmpty()) {
            builder.and(kindergarten.address.containsIgnoreCase(dto.getSigungu()));
        }

        if (dto.getSearch() != null && !dto.getSearch().isEmpty()) {
            builder.and(kindergarten.name.containsIgnoreCase(dto.getSearch()));
        }

        return (List<Kindergarten>) kindergartenRepository.findAll(builder);
    }

}
