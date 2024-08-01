package yeomeong.common.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.auth.SignupRequestDto;
import yeomeong.common.dto.ban.BanJoinRequestDto;
import yeomeong.common.dto.kid.KidJoinRequestDto;
import yeomeong.common.dto.kindergarten.KindergartenSaveRequestDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.dto.member.MemberSaveRequestDto;
import yeomeong.common.entity.member.KidMember;
import yeomeong.common.entity.member.Member;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KidMemberRepository;
import yeomeong.common.repository.KidReposiory;
import yeomeong.common.repository.KindergartenRepository;
import yeomeong.common.repository.MemberRepository;

@Service
@Transactional
@Slf4j
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final KidReposiory kidReposiory;
    private final BanRepository banRepository;
    private final KidMemberRepository kidMemberRepository;
    private final KindergartenRepository kindergartenRepository;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
        BanRepository banRepository, KindergartenRepository kindergartenRepository, KidReposiory kidReposiory,
        KidMemberRepository kidMemberRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.banRepository = banRepository;
        this.kindergartenRepository = kindergartenRepository;
        this.kidReposiory = kidReposiory;
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
        long kidId = kidReposiory.save(
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
                .kid(kidReposiory.findById(kidId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)))
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
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID)));

        memberRepository.updateMemberKindergarten(
            memberId,
            kindergartenRepository.findById(banJoinRequestDto.getKindergartenId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID)));
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

}