package yeomeong.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.exception.ErrorResponse;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.info("[Authorization 실패]");
        response.setHeader("Content-type","application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(ErrorCode.UNAUTHORIZED_ACCESS.getStatus());
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(ErrorCode.UNAUTHORIZED_ACCESS)));
    }

}