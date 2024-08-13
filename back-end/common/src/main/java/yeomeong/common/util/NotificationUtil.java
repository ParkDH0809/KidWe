//package yeomeong.common.util;
//
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.Notification;
//import org.springframework.stereotype.Component;
//import yeomeong.common.dto.notification.NotificationRequestDto;
//
//@Component
//public class NotificationUtil {
//
//    private final String API_URL = "https://fcm.googleapis.com/v1/projects/kidwe-5e131/messages:send";
//    private final FirebaseMessaging firebaseMessaging;
//
//    public NotificationUtil(FirebaseMessaging firebaseMessaging) {
//        this.firebaseMessaging = firebaseMessaging;
//    }
//
//    public String sendMessageToOne(NotificationRequestDto requestDto) {
////        Optional<Users> user = memberRepository.findById(requestDto.getTargetUserId());
//
////        if (user.isPresent()) {
////            if (user.get().getFirebaseToken() != null) {
//        Notification notification = Notification.builder()
//            .setTitle(requestDto.getTitle())
//            .setBody(requestDto.getBody())
//            // .setImage(requestDto.getImage())
//            .build();
//
//        Message message = Message.builder()
//            .setToken(requestDto.getToken())
//            .setNotification(notification)
//             .putAllData(requestDto.getData())
//            .build();
//
//        try {
//            firebaseMessaging.send(message);
//            return "알림을 성공적으로 전송했습니다.";
//        } catch (FirebaseMessagingException e) {
//            e.printStackTrace();
//            return "알림 보내기를 실패하였습니다.";
//        }
////            } else {
////                return "서버에 저장된 해당 유저의 FirebaseToken이 존재하지 않습니다.";
////            }
////
////        } else {
////            return "해당 유저가 존재하지 않습니다. targetUserId=" + requestDto.getTargetUserId();
////        }
//    }
//
//}
