package yeomeong.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import yeomeong.common.dto.member.LoginResponseDto;
import yeomeong.common.security.jwt.JwtUtil;
import yeomeong.common.security.jwt.RefreshTokenService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private RefreshTokenService refreshTokenService;

    @Autowired
    public LoginSuccessHandler(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String accessToken = JwtUtil.createAccessToken(authentication.getName());
        String refreshToken = JwtUtil.createRefreshToken(authentication.getName());

        LoginResponseDto loginResponseDto = new LoginResponseDto(accessToken, refreshToken);

        refreshTokenService.addRefreshToken(authentication.getName(), refreshToken);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        objectMapper.writeValue(response.getWriter(), loginResponseDto);
    }

}