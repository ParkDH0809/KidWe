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

    public List<KindergartenInfoResponseDto> getSearchedKindergartenInfo(KindergartenSearchDto kindergartenSearchDto) {
        return kindergartenEntitiesToDtos(searchKindergartens(kindergartenSearchDto));
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


    private static List<KindergartenInfoResponseDto> kindergartenEntitiesToDtos(List<Kindergarten> kindergartenList) {
        List<KindergartenInfoResponseDto> kindergartenInfoResponseDtos = new ArrayList<>();
        kindergartenList.forEach(kindergartenEntity ->
            kindergartenInfoResponseDtos.add(
                KindergartenInfoResponseDto
                    .builder()
                    .id(kindergartenEntity.getId())
                    .name(kindergartenEntity.getName())
                    .bans(kindergartenEntity.getBans())
                    .tel(kindergartenEntity.getTel())
                    .address(kindergartenEntity.getAddress())
                    .addressDetail(kindergartenEntity.getAddressDetail())
                    .openDate(kindergartenEntity.getOpenDate())
                    .bus(kindergartenEntity.getBus())
                    .build()
            )
        );

        return kindergartenInfoResponseDtos;
    }

}
