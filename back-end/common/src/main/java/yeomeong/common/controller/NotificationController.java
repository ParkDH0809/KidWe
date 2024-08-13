package yeomeong.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.service.NotificationService;

@RestController("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Void> initNotificationToken(Authentication authentication, @RequestParam String notificationToken) {
        notificationService.initNotificationToken(authentication.getName(), notificationToken);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/push")
//    public ResponseEntity<Void> pushMessage(@RequestBody NotificationRequestDto notificationRequestDto) {
//        System.out.println(notificationService.sendMessageTo(notificationRequestDto));
//        return ResponseEntity.ok().build();
//    }

}