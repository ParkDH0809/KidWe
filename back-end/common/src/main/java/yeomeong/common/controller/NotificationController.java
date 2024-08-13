package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.service.NotificationService;

@RestController
@RequestMapping("/notification")
@Tag(name = "알림 API", description = "알림 관련 API")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    @Operation(summary = "알림 Token 저장", description = "회원의 알림 Token을 저장합니다")
    public ResponseEntity<Void> initNotificationToken(Authentication authentication, @RequestParam String notificationToken) {
        notificationService.addNotificationToken(authentication.getName(), notificationToken);
        return ResponseEntity.ok().build();
    }

}