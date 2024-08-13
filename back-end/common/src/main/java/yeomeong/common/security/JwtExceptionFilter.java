package yeomeong.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.exception.ErrorResponse;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, res);
        } catch (CustomException e) {
            log.info("[Auth Exception] exception 발생 {}", e.getMessage());
            setErrorResponse(req, res, e);
        }
    }

    public void setErrorResponse(HttpServletRequest req, HttpServletResponse response, Throwable ex) throws IOException {
        Object exception = req.getAttribute("exception");
        response.setHeader("Content-type","application/json");
        response.setCharacterEncoding("utf-8");
        if(exception instanceof ErrorCode){
            ErrorCode errorCode = (ErrorCode) exception;
            response.getWriter().write(objectMapper.writeValueAsString(
                new ErrorResponse(errorCode)
            ));
            return;
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
    }

}