package yeomeong.common.service;

import org.springframework.stereotype.Service;
import yeomeong.common.entity.member.Member;
import yeomeong.common.repository.MemberRepository;
import yeomeong.common.repository.NotificationRepository;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    public NotificationService(NotificationRepository notificationRepository, MemberRepository memberRepository) {
        this.notificationRepository = notificationRepository;
        this.memberRepository = memberRepository;
    }

    public void initNotificationToken(String email, String token) {
        Member member = memberRepository.findByEmail(email);
        notificationRepository.save(yeomeong.common.entity.member.Notification.of(member, token));
    }

}