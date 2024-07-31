package yeomeong.common.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.auth.JoinRequestDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.dto.member.MemberSaveRequestDto;
import yeomeong.common.entity.member.Member;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.MemberRepository;

@Service
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void joinMember(JoinRequestDto joinRequestDto) {
        Member member = MemberSaveRequestDto.toMemberEntity(joinRequestDto.getMemberSaveRequestDto());
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATED_USER_EMAIL);
        }

        try {
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            memberRepository.save(member);
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    public Member getMemberByEmail(String email) {
        log.debug("[Member Service] email {}", email);
        return memberRepository.findByEmail(email);
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE));
    }

    public MemberProfileResponseDto getMemberProfile(String email) {
        Member member = memberRepository.findByEmail(email);
        return MemberProfileResponseDto
            .builder()
            .id(member.getId())
            .name(member.getName())
            .email(member.getEmail())
            .tel(member.getTel())
            .role(member.getRole())
            .ban(member.getBan())
            .kindergarten(member.getKindergarten())
            .build();
    }

    public void deleteMember(String email) {
        memberRepository.deleteMemberByEmail(email);
    }

}