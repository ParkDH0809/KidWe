package yeomeong.common.controller;

import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.notification.NotificationRequestDto;
import yeomeong.common.service.NotificationService;

@RestController("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/test")
    public ResponseEntity<Void> pushMessage(@RequestBody NotificationRequestDto notificationRequestDto) throws IOException {
        notificationService.sendMessageTo(notificationRequestDto);
        return ResponseEntity.ok().build();
    }

}