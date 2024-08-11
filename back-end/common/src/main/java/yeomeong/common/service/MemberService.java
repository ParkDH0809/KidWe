package yeomeong.common.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.kid.KidBasicInfoResponseDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.dto.member.MemberSaveRequestDto;
import yeomeong.common.dto.member.MemberUpdateRequestDto;
import yeomeong.common.entity.member.Member;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.KidMemberRepository;
import yeomeong.common.repository.MemberRepository;

@Service
@Transactional
@Slf4j
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final KidMemberRepository kidMemberRepository;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, KidMemberRepository kidMemberRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.kidMemberRepository = kidMemberRepository;
    }

    @Transactional
    public void joinMember(MemberSaveRequestDto memberSaveRequestDto) {
        if (memberRepository.findByEmail(memberSaveRequestDto.getEmail()) != null) {
            throw new CustomException(ErrorCode.DUPLICATED_USER_EMAIL);
        }
        Member member = MemberSaveRequestDto.toMemberEntity(memberSaveRequestDto);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public MemberProfileResponseDto getMemberProfile(String email) {
        return MemberProfileResponseDto.toMemberProfileDto(memberRepository.findByEmail(email));
    }

    public void updateMemberProfile(MemberUpdateRequestDto memberUpdateRequestDto, String picture) {
        Member member = memberRepository.findById(memberUpdateRequestDto.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE));
        if (memberUpdateRequestDto.getPassword() != null) {
            member.setPassword(passwordEncoder.encode(memberUpdateRequestDto.getPassword()));
        }
        member.updateFromDto(memberUpdateRequestDto, picture);
    }

    public void deleteMember(String email) {
        memberRepository.deleteMemberByEmail(email);
    }

    public List<KidBasicInfoResponseDto> getChildrenByMember(String email) {
        return kidMemberRepository.findKidMemberByMember_Id(memberRepository.findByEmail(email).getId())
            .stream()
            .map(KidBasicInfoResponseDto::toKidBasicInfoDto)
            .collect(Collectors.toList());
    }

}
