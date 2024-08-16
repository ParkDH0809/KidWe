package yeomeong.common.dto.notification;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.member.PushNotification;

@Getter
@Builder
public class NotificationInfoResponseDto {

    Long notificationId;
    String title;
    String content;
    Boolean isChecked;
    LocalDateTime createTime;

    public static NotificationInfoResponseDto toNotificationInfoResponseDto(PushNotification notification) {
        return NotificationInfoResponseDto.builder()
                .notificationId(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .isChecked(notification.getIsChecked())
                .createTime(notification.getCreateDateTime())
                .build();
    }

}