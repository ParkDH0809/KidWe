package yeomeong.common.util;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import yeomeong.common.dto.notification.NotificationRequestDto;
import yeomeong.common.service.PushNotificationService;

@Slf4j
@Component
public class NotificationUtil {

    private final PushNotificationService pushNotificationService;

    public NotificationUtil(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    public void sendMessages(NotificationRequestDto notificationRequestDto) {
        try {
            log.info("[Notification] 알림 전송 시작: {}", notificationRequestDto.getNotificationContent().getTitle());
            FirebaseMessaging.getInstance().sendEachForMulticast(makeMessages(notificationRequestDto));
            saveNotification(notificationRequestDto);
        } catch (FirebaseMessagingException e) {
            log.info("[FCM Excpetion] 알림 전송 중 오류가 발생했습니다: {}", e.getMessage());
        }
    }

    private MulticastMessage makeMessages(NotificationRequestDto notificationRequestDto) {
        Notification notification = Notification.builder()
                .setTitle(notificationRequestDto.getNotificationContent().getTitle())
                .setBody(notificationRequestDto.getNotificationContent().getContent())
                .build();
        return MulticastMessage.builder()
                .setNotification(notification)
                .addAllTokens(notificationRequestDto.getToken())
                .build();
    }

    private void saveNotification(NotificationRequestDto notificationRequestDto) {
        for (String email : notificationRequestDto.getEmail()) {
            pushNotificationService.addNotificationMessage(email, notificationRequestDto.getNotificationContent());
        }
    }

}