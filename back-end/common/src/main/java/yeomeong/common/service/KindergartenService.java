package yeomeong.common.service;

import com.querydsl.core.BooleanBuilder;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.kindergarten.KindergartenInfoResponseDto;
import yeomeong.common.dto.kindergarten.KindergartenSaveRequestDto;
import yeomeong.common.dto.kindergarten.KindergartenSearchRequestDto;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.kindergarten.QKindergarten;
import yeomeong.common.entity.member.atype;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.KindergartenRepository;
import yeomeong.common.repository.MemberRepository;

@Service
@Slf4j
public class KindergartenService {

    private final KindergartenRepository kindergartenRepository;
    private final MemberRepository memberRepository;

    public KindergartenService(KindergartenRepository kindergartenRepository, MemberRepository memberRepository) {
        this.kindergartenRepository = kindergartenRepository;
        this.memberRepository = memberRepository;
    }

    public void createKindergarten(KindergartenSaveRequestDto kindergartenSaveRequestDto) {
        long kindergartenId = kindergartenRepository.save(KindergartenSaveRequestDto.toKindergartenEntity(kindergartenSaveRequestDto)).getId();
        memberRepository.updateMemberKindergarten(kindergartenSaveRequestDto.getMemberId(),
            kindergartenRepository.findById(kindergartenId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)));
        memberRepository.updateMemberStatus(kindergartenSaveRequestDto.getMemberId(), atype.ACCEPT);
    }

    public KindergartenInfoResponseDto getKindergartenInfo(Long kindergartenId) {
        return KindergartenInfoResponseDto.toKindergartenDto(kindergartenRepository.findById(kindergartenId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID)));
    }

    public List<KindergartenInfoResponseDto> getSearchedKindergartenInfo(KindergartenSearchRequestDto kindergartenSearchRequestDto) {
        List<KindergartenInfoResponseDto> kindergartenInfoResponseDtos = new ArrayList<>();
        searchKindergartens(kindergartenSearchRequestDto).forEach(kindergartenEntity ->
            kindergartenInfoResponseDtos.add(KindergartenInfoResponseDto.toKindergartenDto(kindergartenEntity)));
        return kindergartenInfoResponseDtos;
    }

    public List<Kindergarten> searchKindergartens(KindergartenSearchRequestDto dto) {
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
