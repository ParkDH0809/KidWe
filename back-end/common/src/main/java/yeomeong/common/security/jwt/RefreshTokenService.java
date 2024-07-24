package yeomeong.common.security.jwt;

import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void addRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    public boolean findRefreshToken(String refreshToken) {
        if(!refreshTokenRepository.existsByRefreshToken(refreshToken)) {
            System.out.println("refreshToken not found");
            return false;
        }

        if(!JwtUtil.getLoginEmail(JwtUtil.removeBearerPrefix(refreshToken)).equals(getMemberEmail(refreshToken))) {
            System.out.println("refreshToken not found");
            return false;
        }

        return true;
    }

    public String getMemberEmail(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken).getMemberEmail();
    }

    public String createAccessToken(String email) {
        return JwtUtil.createAccessToken(email);
    }

}