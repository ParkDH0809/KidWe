package yeomeong.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /* 200 */
    SUCCESS(200, "OK", "요청에 성공하였습니다."),

    /* 400 */
    INVALID_INPUT_VALUE(400, "INVALID_INPUT_VALUE", "입력값이 올바르지 않습니다."),
    POST_NOT_FOUND(404, "POST_NOT_FOUND", "존재하지 않는 게시물입니다."),
    DUPLICATED_USER_EMAIL(409, "DUPLICATED_USER_EMAIL", "이미 회원가입된 이메일입니다.");


    private final int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}