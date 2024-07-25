package yeomeong.common.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.token.refresh-expired-time}")
    private long refreshExpiredTime;
    private final JwtRepository refreshTokenRepository;

    public JwtService(JwtRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void addRefreshToken(String email, String refreshToken) {
        refreshTokenRepository.save(email, refreshToken, refreshExpiredTime);
    }

    public boolean isCorrectToken(String refreshToken) {
        if(refreshTokenRepository.findByEmail(JwtUtil.getLoginEmail(JwtUtil.removeBearerPrefix(refreshToken))) == null) {
            log.info("[Request - new Refresh Token] Refresh token과 email 정보가 일치하지 않음");
            return false;
        }
        return true;
    }

    public String createAccessToken(String token) {
        return JwtUtil.createAccessToken(JwtUtil.getLoginEmail(JwtUtil.removeBearerPrefix(token)));
    }

}