package yeomeong.common.service;

import org.springframework.stereotype.Service;
import yeomeong.common.entity.member.Member;
import yeomeong.common.repository.MemberRepository;

@Service
public class NotificationService {

    private final MemberRepository memberRepository;

    public NotificationService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void addNotificationToken(String email, String token) {
        Member member = memberRepository.findByEmail(email);
        memberRepository.updateNotificationToken(member.getId(), token);
    }

}