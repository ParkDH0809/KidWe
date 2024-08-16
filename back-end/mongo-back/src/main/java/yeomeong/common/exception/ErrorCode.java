package yeomeong.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /* 200 */
    SUCCESS(200, "OK", "요청에 성공하였습니다."),

    /* 400 */
    INVALID_INPUT_VALUE(400, "INVALID_INPUT_VALUE", "입력값이 올바르지 않습니다."),
    INVALID_DATE_VALUE(400, "INVALID_DATE_VALUE", "입력한 날짜 값이 올바르지 않습니다."),

    NOT_RESPONSE(413, "NOT_RESPONSE_OPENAI", "Open AI가 응답하지 않습니다"),

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