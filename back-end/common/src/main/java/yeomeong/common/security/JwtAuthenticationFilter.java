package yeomeong.common.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import yeomeong.common.entity.member.Member;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain)
        throws ServletException, IOException, ExpiredJwtException {

        try {
            log.info("[JwtAuthenticationFilter start] {}", request.getRequestURI());
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader == null) {
                log.info("[JwtAuthenticationFilter] Authorization header is null");
                filterChain.doFilter(request, response);
                return;
            }

            if (!authorizationHeader.startsWith("Bearer ")) {
                log.info("[JwtAuthenticationFilter] Authorization header is not starting with Bearer");
                filterChain.doFilter(request, response);
                return;
            }

            log.info("[JwtAuthenticationFilter] Authorization header: {}", authorizationHeader);
            if (jwtService.isTokenStored(authorizationHeader) && !request.getRequestURI().equals("/refresh")) {
                log.info("[JwtAuthenticationFilter] This token is refresh token");
                filterChain.doFilter(request, response);
                return;
            }

            log.info("[JwtAuthenticationFilter] Token is valid");
            if (!jwtService.isTokenStored(authorizationHeader) && request.getRequestURI().equals("/refresh")) {
                log.info("[JwtAuthenticationFilter] This token is access token");
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtService.isLogoutAccessToken(authorizationHeader)) {
                log.info("[JwtAuthenticationFilter] Logout access token");
                filterChain.doFilter(request, response);
                return;
            }

            if (JwtUtil.isExpired(authorizationHeader)) {
                log.info("[JwtAuthenticationFilter] Token is expired");
                filterChain.doFilter(request, response);
                return;
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
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            throw new JwtException("토큰 기한 만료");
        }
    }

}