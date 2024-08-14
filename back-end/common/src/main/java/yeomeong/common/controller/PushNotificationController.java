package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.notification.NotificationDeleteRequestDto;
import yeomeong.common.dto.notification.NotificationInfoResponseDto;
import yeomeong.common.dto.notification.NotificationSaveRequestDto;
import yeomeong.common.service.PushNotificationService;

@Slf4j
@RestController
@RequestMapping("/notification")
@Tag(name = "알림 API", description = "알림 관련 API")
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("/token")
    @Operation(summary = "알림 Token 저장", description = "회원의 알림 Token을 저장합니다.")
    public ResponseEntity<Void> initNotificationToken(Authentication authentication, @RequestParam String notificationToken) {
        log.info("[initNotificationToken] email: {}, token: {}", authentication.getName(), notificationToken);
        pushNotificationService.addNotificationToken(authentication.getName(), notificationToken);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Operation(summary = "알림 내용 저장", description = "회원의 알림 메시지를 저장합니다.")
    public ResponseEntity<Void> addNotificationMessage(Authentication authentication,
            @RequestBody NotificationSaveRequestDto notificationSaveRequestDto) {
        pushNotificationService.addNotificationMessage(authentication.getName(), notificationSaveRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "알림 내역 조회", description = "회원의 알림 내역을 조회합니다.")
    public ResponseEntity<List<NotificationInfoResponseDto>> getNotifications(Authentication authentication) {
        return ResponseEntity.ok().body(pushNotificationService.getNotifications(authentication.getName()));
    }

    @PutMapping
    @Operation(summary = "알림 내용 읽음", description = "회원의 알림 메시지를 읽음 처리합니다.")
    public ResponseEntity<Void> updateNotificationChecked(@RequestParam Long notificationId) {
        pushNotificationService.updateNotificationChecked(notificationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(summary = "알림 내용 삭제", description = "회원의 알림 메시지를 삭제 처리합니다.")
    public ResponseEntity<Void> deleteNotificationChecked(@RequestBody NotificationDeleteRequestDto notificationDeleteRequestDto) {
        pushNotificationService.deleteNotificationChecked(notificationDeleteRequestDto);
        return ResponseEntity.ok().build();
    }

}
