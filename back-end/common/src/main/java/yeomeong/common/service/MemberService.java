package yeomeong.common.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.auth.SignupRequestDto;
import yeomeong.common.dto.ban.BanJoinRequestDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.dto.member.MemberSaveRequestDto;
import yeomeong.common.entity.member.Member;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KindergartenRepository;
import yeomeong.common.repository.MemberRepository;

@Service
@Transactional
@Slf4j
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final BanRepository banRepository;
    private final KindergartenRepository kindergartenRepository;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
        BanRepository banRepository, KindergartenRepository kindergartenRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.banRepository = banRepository;
        this.kindergartenRepository = kindergartenRepository;
    }

    @Transactional
    public void joinMember(SignupRequestDto signupRequestDto) {
        try {
            Member member = MemberSaveRequestDto.toMemberEntity(signupRequestDto.getMember());
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            memberRepository.save(member);
            switch (member.getRole()) {
                case ROLE_GUARDIAN -> joinGuardian();

                case ROLE_DIRECTOR -> joinDirector();

                case ROLE_TEACHER ->
                    joinTeacher(memberRepository.findByEmail(member.getEmail()), signupRequestDto.getBan());
            }
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    private void joinGuardian() {

    }

    private void joinDirector() {

    }

    private void joinTeacher(Member member, BanJoinRequestDto banJoinRequestDto) {
        memberRepository.updateMember(
            member.getId(),
            banRepository.findById(banJoinRequestDto.getBanId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID)),
            kindergartenRepository.findById(banJoinRequestDto.getKindergartenId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID))
        );
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