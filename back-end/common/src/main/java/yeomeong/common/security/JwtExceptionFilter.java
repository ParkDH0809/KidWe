package yeomeong.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.exception.ErrorResponse;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, res);
        } catch (JwtException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, res, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) throws IOException {
        response.setHeader("Content-type","application/json");
        response.setCharacterEncoding("utf-8");

        response.setStatus(status.value());
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(ErrorCode.UNAUTHENTICATED_ACCESS_TOKEN)));
    }

}