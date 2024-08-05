package yeomeong.common.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.auth.SignupRequestDto;
import yeomeong.common.dto.ban.BanJoinRequestDto;
import yeomeong.common.dto.kid.KidBasicInfoDto;
import yeomeong.common.dto.kid.KidJoinRequestDto;
import yeomeong.common.dto.kindergarten.KindergartenApprovalStatusDto;
import yeomeong.common.dto.kindergarten.KindergartenSaveRequestDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.dto.member.MemberSaveRequestDto;
import yeomeong.common.dto.member.TeacherChangeBanRequestDto;
import yeomeong.common.dto.member.TeacherDetailInfoDto;
import yeomeong.common.entity.member.KidMember;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.atype;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KidMemberRepository;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.KindergartenRepository;
import yeomeong.common.repository.MemberRepository;

@Service
@Transactional
@Slf4j
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final KidRepository kidRepository;
    private final BanRepository banRepository;
    private final KidMemberRepository kidMemberRepository;
    private final KindergartenRepository kindergartenRepository;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
        BanRepository banRepository, KindergartenRepository kindergartenRepository, KidRepository kidRepository,
        KidMemberRepository kidMemberRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.banRepository = banRepository;
        this.kindergartenRepository = kindergartenRepository;
        this.kidRepository = kidRepository;
        this.kidMemberRepository = kidMemberRepository;
    }

    @Transactional
    public void joinMember(SignupRequestDto signupRequestDto) {
        Member member = MemberSaveRequestDto.toMemberEntity(signupRequestDto.getMember());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        long memberId = memberRepository.save(member).getId();
        switch (member.getRole()) {
            case ROLE_GUARDIAN -> joinGuardian(memberId, signupRequestDto.getKid());
            case ROLE_DIRECTOR -> joinDirector(memberId, signupRequestDto.getKindergarten());
            case ROLE_TEACHER -> joinTeacher(memberId, signupRequestDto.getBan());
            default -> throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    private void joinGuardian(long memberId, KidJoinRequestDto kidJoinRequestDto) {
        long kidId = kidRepository.save(
            KidJoinRequestDto.toKidEntity(
                kidJoinRequestDto,
                kindergartenRepository.findById(kidJoinRequestDto.getKindergartenId())
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)),
                banRepository.findById(kidJoinRequestDto.getBanId())
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)))
        ).getId();

        kidMemberRepository.save(
            KidMember
                .builder()
                .kid(kidRepository.findById(kidId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)))
                .member(memberRepository.findById(memberId)
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)))
                .build()
        );
    }

    private void joinDirector(long memberId, KindergartenSaveRequestDto kindergartenSaveRequestDto) {
        long kindergartenId = kindergartenRepository.save(
            KindergartenSaveRequestDto.toKindergartenEntity(kindergartenSaveRequestDto)).getId();

        memberRepository.updateMemberKindergarten(memberId,
            kindergartenRepository.findById(kindergartenId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)));
    }

    private void joinTeacher(long memberId, BanJoinRequestDto banJoinRequestDto) {
        memberRepository.updateMemberBan(
            memberId,
            banRepository.findById(banJoinRequestDto.getBanId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID)));

        memberRepository.updateMemberKindergarten(
            memberId,
            kindergartenRepository.findById(banJoinRequestDto.getKindergartenId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID)));
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public MemberProfileResponseDto getMemberProfile(String email) {
        return MemberProfileResponseDto.toMemberProfileDto(memberRepository.findByEmail(email));
    }

    public void deleteMember(String email) {
        memberRepository.deleteMemberByEmail(email);
    }

    public List<KidBasicInfoDto> getChildrenByMember(String email) {
        return kidMemberRepository.findKidMemberByMember_Id(memberRepository.findByEmail(email).getId())
            .stream()
            .map(KidBasicInfoDto::toKidBasicInfoDto)
            .collect(Collectors.toList());
    }

    public void updateMemberState(KindergartenApprovalStatusDto approvalStatusDto) {
        if (kidMemberRepository.updateMemberStatusById(approvalStatusDto.getTeacherId(), approvalStatusDto.getStatus()) != 1) {
            throw new CustomException(ErrorCode.NOT_FOUND_ID);
        }
    }

    public List<TeacherDetailInfoDto> getTeachersByStatus(Long id, atype status) {
        List<TeacherDetailInfoDto> teacherDetailInfos = new ArrayList<>();
        memberRepository.findMemberByKindergartenId(id)
            .stream()
            .filter(member -> member.getMemberStatus() == status)
            .toList()
            .forEach(m -> teacherDetailInfos.add(TeacherDetailInfoDto.toTeacherDetailInfoDto(m)));
        return teacherDetailInfos;
    }

    public void changeTeachersBan(TeacherChangeBanRequestDto teacherChangeBanRequestDto) {
        memberRepository.updateMemberBan(
            teacherChangeBanRequestDto.getTeacherId(),
            banRepository.findById(teacherChangeBanRequestDto.getBanId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID)));
    }

}
