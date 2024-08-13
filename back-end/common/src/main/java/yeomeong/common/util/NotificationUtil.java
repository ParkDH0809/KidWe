package yeomeong.common.util;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import yeomeong.common.dto.notification.NotificationRequestDto;

@Slf4j
public class NotificationUtil {

    public static void sendMessages(NotificationRequestDto notificationRequestDto) {
        try {
            FirebaseMessaging.getInstance().sendEachForMulticast(makeMessages(notificationRequestDto));
        } catch (FirebaseMessagingException e) {
            log.info("[FCM Excpetion] 알림 전송 중 오류가 발생했습니다: {}", e.getMessage());
        }
    }

    private static MulticastMessage makeMessages(NotificationRequestDto notificationRequestDto) {
        Notification notification = Notification.builder()
            .setTitle(notificationRequestDto.getNotificationContent().getTitle())
            .setBody(notificationRequestDto.getNotificationContent().getBody())
            .build();
        return MulticastMessage.builder()
            .setNotification(notification)
            .addAllTokens(notificationRequestDto.getToken())
            .build();
    }

}