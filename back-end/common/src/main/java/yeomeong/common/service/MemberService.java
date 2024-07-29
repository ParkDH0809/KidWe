package yeomeong.common.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.member.JoinRequestDto;
import yeomeong.common.entity.jpa.member.Member;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.jpa.MemberRepository;

@Service
@Transactional
@Slf4j
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void joinMember(JoinRequestDto joinRequestDto) {
        Member member = JoinRequestDto.toMemberEntity(joinRequestDto);
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

}