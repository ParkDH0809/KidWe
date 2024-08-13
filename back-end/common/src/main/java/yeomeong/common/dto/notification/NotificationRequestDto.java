package yeomeong.common.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {

    private String email;
    private String title;
    private String body;

}