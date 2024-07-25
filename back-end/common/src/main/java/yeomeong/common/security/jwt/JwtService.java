package yeomeong.common.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtService {

    private final static String LOGOUT = "logout";
    private final JwtRepository refreshTokenRepository;

    @Value("${jwt.token.refresh-expired-time}")
    private long refreshExpiredTime;

    public JwtService(JwtRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void saveRefreshToken(String email, String refreshToken) {
        refreshTokenRepository.save(email, refreshToken, refreshExpiredTime);
    }

    public void saveLogoutAccessToken(String accessToken) {
        log.debug("[JwtService - saveLogoutAccessToken] {}", accessToken);
        refreshTokenRepository.save(accessToken, LOGOUT, JwtUtil.getExpiredTime(JwtUtil.removeBearerPrefix(accessToken)));
    }

    public boolean isCorrectToken(String refreshToken) {
        if(refreshTokenRepository.findByKey(JwtUtil.getLoginEmail(JwtUtil.removeBearerPrefix(refreshToken))) == null) {
            log.debug("[JwtService - isCorrectToken] Refresh token과 email 정보가 일치하지 않음");
            return false;
        }
        return true;
    }

    public boolean isLogoutAccessToken(String accessToken) {
        return refreshTokenRepository.findByKey(JwtUtil.getLoginEmail(JwtUtil.removeBearerPrefix(accessToken))) != null;
    }

    public String createAccessToken(String token) {
        return JwtUtil.createAccessToken(JwtUtil.getLoginEmail(JwtUtil.removeBearerPrefix(token)));
    }

    public void deleteRefreshToken(String email) {
        refreshTokenRepository.deleteRefreshToken(email);
    }

}