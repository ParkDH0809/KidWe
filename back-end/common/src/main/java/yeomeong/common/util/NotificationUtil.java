package yeomeong.common.util;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import java.util.List;
import yeomeong.common.dto.notification.NotificationRequestDto;

public class NotificationUtil {

    public static void sendMessages(NotificationRequestDto notificationRequestDto) throws FirebaseMessagingException {
        FirebaseMessaging.getInstance().sendEachForMulticast(
            makeMessages(notificationRequestDto.getTitle(), notificationRequestDto.getBody(), notificationRequestDto.getToken()));
    }

    public static MulticastMessage makeMessages(String title, String body, List<String> targetTokens) {
        Notification notification = Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build();
        return MulticastMessage.builder()
            .setNotification(notification)
            .addAllTokens(targetTokens)
            .build();
    }

}