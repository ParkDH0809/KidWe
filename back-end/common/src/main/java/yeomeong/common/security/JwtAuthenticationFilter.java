package yeomeong.common.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import yeomeong.common.entity.member.Member;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.security.jwt.JwtService;
import yeomeong.common.security.jwt.JwtUtil;
import yeomeong.common.service.MemberService;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException, ExpiredJwtException {
        if (request.getRequestURI().equals("/login") ||
            request.getRequestURI().equals("/logout") ||
            request.getRequestURI().equals("/refresh") ||
            request.getRequestURI().equals("/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            log.info("[JwtAuthenticationFilter start] {}", request.getRequestURI());
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader == null) {
                log.info("[JwtAuthenticationFilter] Authorization header is null");
                SecurityContextHolder.getContext().setAuthentication(null);
                throw new CustomException(ErrorCode.TOKEN_MISSING);
            }

            if (!authorizationHeader.startsWith("Bearer ")) {
                log.info("[JwtAuthenticationFilter] Authorization header is not starting with Bearer");
                throw new CustomException(ErrorCode.TOKEN_NOT_BEARER);
            }

            if (jwtService.isTokenStored(authorizationHeader) && !request.getRequestURI().equals("/refresh")) {
                log.info("[JwtAuthenticationFilter] This token is refresh token");
                throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_ALLOWED);
            }

            if (!jwtService.isTokenStored(authorizationHeader) && request.getRequestURI().equals("/refresh")) {
                log.info("[JwtAuthenticationFilter] This token is access token");
                throw new CustomException(ErrorCode.ACCESS_TOKEN_REQUIRED);
            }

            if (jwtService.isLogoutAccessToken(authorizationHeader)) {
                log.info("[JwtAuthenticationFilter] Logout access token");
                throw new CustomException(ErrorCode.LOGGED_OUT_ACCESS_TOKEN);
            }

            if (JwtUtil.isExpired(authorizationHeader)) {
                log.info("[JwtAuthenticationFilter] Token is expired");
                throw new CustomException(ErrorCode.EXPIRED_TOKEN);
            }

            log.info("[JwtAuthenticationFilter] Token is valid");
            Member loginMember = memberService.getMemberByEmail(
                JwtUtil.getLoginEmail(authorizationHeader));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetailsService.loadUserByUsername(loginMember.getEmail()),
                loginMember.getPassword(),
                List.of(new SimpleGrantedAuthority(loginMember.getRole().toString())));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (CustomException e) {
            request.setAttribute("exception", e.getErrorCode());
            throw new CustomException(e.getErrorCode());
        } catch (ExpiredJwtException e) {
            if (request.getRequestURI().equals("/refresh")) {
                request.setAttribute("exception", ErrorCode.UNAUTHENTICATED_EXPIRED_REFRESH_TOKEN);
                throw new CustomException(ErrorCode.UNAUTHENTICATED_EXPIRED_REFRESH_TOKEN);
            } else {
                request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN);
                throw new CustomException(ErrorCode.EXPIRED_TOKEN);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return StringUtils.startsWithAny(request.getRequestURI()
            , "/swagger", "/swagger-ui.html", "/swagger-ui/*", "/api-docs", "/api-docs/*", "/v3/api-docs/*"
            , "/v3/api-docs/swagger-config", "/v3/api-docs/KIDWE");
    }

}
