package yeomeong.common.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.notification.NotificationDeleteRequestDto;
import yeomeong.common.dto.notification.NotificationInfoResponseDto;
import yeomeong.common.dto.notification.NotificationRequestDto;
import yeomeong.common.dto.notification.NotificationSaveRequestDto;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.PushNotification;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.MemberRepository;
import yeomeong.common.repository.PushNotificationRepository;
import yeomeong.common.util.NotificationUtil;

@Slf4j
@Service
public class PushNotificationService {

    private final MemberRepository memberRepository;
    private final PushNotificationRepository pushNotificationRepository;

    public PushNotificationService(MemberRepository memberRepository, PushNotificationRepository pushNotificationRepository) {
        this.memberRepository = memberRepository;
        this.pushNotificationRepository = pushNotificationRepository;
    }

    public void addNotificationToken(String email, String token) {
        Member member = memberRepository.findByEmail(email);
        memberRepository.updateNotificationToken(member.getId(), token);
    }

    public void addNotificationMessage(String email, NotificationSaveRequestDto dto) {
        Member member = memberRepository.findByEmail(email);
        pushNotificationRepository.save(PushNotification.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .member(member)
                        .createDateTime(LocalDateTime.now())
                        .isDeleted(false)
                        .isChecked(false)
                .build());
    }

    public List<NotificationInfoResponseDto> getNotifications(String email) {
        List<PushNotification> pushNotification = pushNotificationRepository.findByMember_EmailAndIsDeletedFalse(email);
        List<NotificationInfoResponseDto> responseNotifications = new ArrayList<>();
        for(PushNotification push : pushNotification) {
            responseNotifications.add(NotificationInfoResponseDto.toNotificationInfoResponseDto(push));
        }
        return responseNotifications;
    }

    public void updateNotificationChecked(Long notificationId) {
        pushNotificationRepository.updateNotificationChecked(notificationId);
    }

    public void deleteNotificationChecked(NotificationDeleteRequestDto notificationDeleteRequestDto) {
        for (Long notificationId : notificationDeleteRequestDto.getNotificationIds()) {
            pushNotificationRepository.deleteNotificationChecked(notificationId);
        }
    }

    public void testNotification(String email) {
        try {
            Member member = memberRepository.findByEmail(email);
            NotificationUtil.sendMessages(NotificationRequestDto.builder()
                    .token(List.of(memberRepository.getNotificationTokenBayMemberId(member.getId())
                            .orElseThrow(() -> new CustomException(ErrorCode.NOTIFICATION_TOKEN_MISSING))))
                    .notificationContent(NotificationContent.TEST)
                    .build());
        } catch (CustomException e) {
            log.info("[Notification] 알림 토큰이 없는 회원입니다.");
        }
    }


}