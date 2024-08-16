package yeomeong.common.dto.notification;

import java.util.List;
import lombok.Getter;

@Getter
public class NotificationDeleteRequestDto {

    List<Long> notificationIds;

}
