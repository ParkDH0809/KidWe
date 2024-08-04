package yeomeong.common.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.ban.BanBasicInfoDto;
import yeomeong.common.dto.ban.BanCreateRequestDto;
import yeomeong.common.dto.ban.BanDetailInfoDto;
import yeomeong.common.dto.ban.BanNameChangeRequestDto;
import yeomeong.common.dto.member.TeacherBasicInfoDto;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Member;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KindergartenRepository;
import yeomeong.common.repository.MemberRepository;

@Service
public class BanService {

    final BanRepository banRepository;
    final KindergartenRepository kindergartenRepository;
    private final MemberRepository memberRepository;

    public BanService(BanRepository banRepository, KindergartenRepository kindergartenRepository, MemberRepository memberRepository) {
        this.banRepository = banRepository;
        this.kindergartenRepository = kindergartenRepository;
        this.memberRepository = memberRepository;
    }

    public void createBan(BanCreateRequestDto banCreateRequestDto) {
        banRepository.save(BanCreateRequestDto.toBanEntity(
            kindergartenRepository.findById(banCreateRequestDto.getKindergartenId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID)),
            banCreateRequestDto));
    }

    public List<BanDetailInfoDto> getBansIdByKindergartenId(Long kindergartenId) {
        List<Ban> bans = banRepository.findByKindergarten_Id(kindergartenId);
        List<BanDetailInfoDto> banDetailInfos = new ArrayList<>();
        for (Ban ban : bans) {
            banDetailInfos.add(getBanInfo(ban.getId()));
        }
        return banDetailInfos;
    }

    public BanDetailInfoDto getBanInfo(Long banId) {
        List<Member> teachers = memberRepository.findMemberByBanId(banId);
        BanDetailInfoDto banDetailInfoDto = BanDetailInfoDto.toBanDetailInfoDto(
            banRepository.findById(banId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID)));
        List<TeacherBasicInfoDto> teacherBasicInfos = new ArrayList<>();
        for(Member teacher : teachers) {
            teacherBasicInfos.add(TeacherBasicInfoDto.toTeacherInfoDto(teacher));
        }
        banDetailInfoDto.initializeDefaults(teacherBasicInfos);
        return banDetailInfoDto;
    }

    public void changeBanName(BanNameChangeRequestDto banNameChangeRequestDto) {
        if (banRepository.changeBanName(banNameChangeRequestDto.getId(), banNameChangeRequestDto.getName()) != 1) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    public List<BanBasicInfoDto> getBansBasicInfoByKindergarten(Long kindergartenId) {
        List<BanBasicInfoDto> banBasicInfoDtos = new ArrayList<>();
        banRepository.findByKindergarten_Id(kindergartenId).forEach(banEntity ->
            banBasicInfoDtos.add(BanBasicInfoDto.toBanBasicInfoDto(banEntity)));
        return banBasicInfoDtos;
    }

}
