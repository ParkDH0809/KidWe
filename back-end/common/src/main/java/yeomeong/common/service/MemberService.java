package yeomeong.common.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.member.JoinRequestDto;
import yeomeong.common.entity.jpa.member.Member;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.jpa.MemberRepository;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public void joinMember(JoinRequestDto joinRequestDto) {
        Member member = JoinRequestDto.toMemberEntity(joinRequestDto);
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATED_USER_EMAIL);
        }

        try {
            memberRepository.save(member);
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.DUPLICATED_USER_EMAIL);
        }
    }

}