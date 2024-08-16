package yeomeong.common.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.ban.BanCreateRequestDto;
import yeomeong.common.dto.ban.BanDetailInfoResponseDto;
import yeomeong.common.dto.ban.BanNameChangeRequestDto;
import yeomeong.common.dto.member.TeacherBasicInfoResponseDto;
import yeomeong.common.dto.ban.BanChangeRequestDto;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Member;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.KindergartenRepository;
import yeomeong.common.repository.MemberRepository;

@Service
public class BanService {

    final BanRepository banRepository;
    final KindergartenRepository kindergartenRepository;
    private final MemberRepository memberRepository;
    private final KidRepository kidRepository;

    public BanService(BanRepository banRepository, KindergartenRepository kindergartenRepository, MemberRepository memberRepository,
        KidRepository kidRepository) {
        this.banRepository = banRepository;
        this.kindergartenRepository = kindergartenRepository;
        this.memberRepository = memberRepository;
        this.kidRepository = kidRepository;
    }

    public void createBan(BanCreateRequestDto banCreateRequestDto) {
        banRepository.save(BanCreateRequestDto.toBanEntity(
            kindergartenRepository.findById(banCreateRequestDto.getKindergartenId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID)),
            banCreateRequestDto));
    }

    public List<BanDetailInfoResponseDto> getBansIdByKindergartenId(Long kindergartenId) {
        List<Ban> bans = banRepository.findByKindergarten_Id(kindergartenId);
        List<BanDetailInfoResponseDto> banDetailInfos = new ArrayList<>();
        for (Ban ban : bans) {
            banDetailInfos.add(getBanInfo(ban.getId()));
        }
        return banDetailInfos;
    }

    public BanDetailInfoResponseDto getBanInfo(Long banId) {
        List<Member> teachers = memberRepository.findMemberByBanId(banId);
        BanDetailInfoResponseDto banDetailInfoDto = BanDetailInfoResponseDto.toBanDetailInfoDto(
            banRepository.findById(banId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID)));
        List<TeacherBasicInfoResponseDto> teacherBasicInfos = new ArrayList<>();
        for(Member teacher : teachers) {
            teacherBasicInfos.add(TeacherBasicInfoResponseDto.toTeacherInfoDto(teacher));
        }
        banDetailInfoDto.initializeDefaults(teacherBasicInfos);
        return banDetailInfoDto;
    }

    public void changeBanName(BanNameChangeRequestDto banNameChangeRequestDto) {
        if (banRepository.changeBanName(banNameChangeRequestDto.getId(), banNameChangeRequestDto.getName()) != 1) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    public void updateTeachersBan(BanChangeRequestDto banChangeRequestDto) {
        memberRepository.updateMemberBan(banChangeRequestDto.getId(),
            banRepository.findById(banChangeRequestDto.getBanId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID)));
    }

    public void updateKidsBan(BanChangeRequestDto banChangeRequestDto) {
        kidRepository.updateKidBan(banChangeRequestDto.getId(),
            banRepository.findById(banChangeRequestDto.getBanId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID)));
    }

}
