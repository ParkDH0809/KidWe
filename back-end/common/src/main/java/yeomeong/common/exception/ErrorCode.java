package yeomeong.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /* 200 */
    SUCCESS(200, "OK", "요청에 성공하였습니다."),

    /* 400 */
    INVALID_INPUT_VALUE(400, "INVALID_INPUT_VALUE", "입력값이 올바르지 않습니다."),
    INVALID_DATE_VALUE(400, "INVALID_DATE_VALUE", "입력한 날짜 값이 올바르지 않습니다."),
    UPDATE_FAILED(400, "UPDATE_FAILED", "올바르지 않은 입력 값이 있습니다. 입력 정보를 다시 확인해주세요."),
    NO_CHANGES_DETECTED(400, "NO_CHANGES_DETECTED", "변경된 정보가 없습니다. 입력 정보를 다시 확인해주세요."),
    INVALID_BAN_ID(400, "INVALID_BAN_ID", "변경할 반 정보가 올바르지 않습니다. 입력 정보를 다시 확인해주세요."),
    INVALID_TOKEN(400, "INVALID_TOKEN", "토큰이 올바르지 않습니다."),

    INVALID_LOGIN_VALUE(401, "INVALID_LOGIN_VALUE", "로그인 정보가 올바르지 않습니다."),
    UNAUTHORIZED_WRITER(401, "UNAUTHORIZED_WRITER", "작성자가 아닙니다"),
    UNAUTHENTICATED_EXPIRED_REFRESH_TOKEN(401, "UNAUTHENTICATED_EXPIRED_REFRESH_TOKEN", "만료된 리프레시 토큰입니다."),

    NOT_FOUND_ID(404, "NOT_FOUND_ID", "존재하지 않는 ID입니다."),
    NOT_FOUND_KID(404, "NOT_FOUND_KID", "존재하지 않는 원생입니다"),
    NOT_FOUND_DAILYNOTE_ID(404, "NOT_FOUND_DAILYNOTE_ID", "존재하지 않는 알림장입니다."),
    NOT_FOUND_DAILYNOTE_COMMENT_ID(404, "NOT_FOUND_DAILYNOTE_COMMENT_ID", "존재하지 않는 알림장 댓글입니다."),
    NOT_FOUND_WRITER(404, "NOT_FOUND_WRITER", "존재하지 않는 작성자ID입니다"),
    NOT_FOUND_POST(404, "NOT_FOUND_POST", "존재하지 않는 게시물입니다."),

    DUPLICATED_USER_EMAIL(409, "DUPLICATED_USER_EMAIL", "이미 회원가입된 이메일입니다."),

    REPOSITORY_ERROR(500, "REPOSITORY_ERROR", "레포지토리에서 문제가 생겼습니다");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}