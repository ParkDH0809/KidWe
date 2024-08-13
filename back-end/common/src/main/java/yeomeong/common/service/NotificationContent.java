package yeomeong.common.service;

import lombok.Getter;

@Getter
public enum NotificationContent {

    JOIN_APPROVAL("가입 승인", "유치원 가입이 승인되었습니다."),
    JOIN_DECLINE("가입 거절", "유치원 가입이 거절되었습니다.");

    private String title;
    private String body;

    NotificationContent(String title, String body) {
        this.title = title;
        this.body = body;
    }

}
