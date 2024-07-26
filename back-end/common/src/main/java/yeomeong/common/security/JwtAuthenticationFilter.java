package yeomeong.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import yeomeong.common.entity.jpa.member.Member;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.security.jwt.JwtService;
import yeomeong.common.security.jwt.JwtUtil;
import yeomeong.common.service.MemberService;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        log.debug("[JwtAuthenticationFilter start]");
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null) {
            log.debug("[JwtAuthenticationFilter] Authorization header is null");
            filterChain.doFilter(request, response);
            return;
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            log.debug("[JwtAuthenticationFilter] Authorization header is not starting with Bearer");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            objectMapper.writeValue(response.getWriter(),
                new CustomException(ErrorCode.INVALID_TOKEN));
        }

        if(jwtService.isCorrectRefreshToken(authorizationHeader) && !request.getRequestURI().equals("/refresh")) {
            log.debug("[JwtAuthenticationFilter] This token is refresh token");
            filterChain.doFilter(request, response);
            return;
        }

        if(!jwtService.isCorrectRefreshToken(authorizationHeader) && request.getRequestURI().equals("/refresh")) {
            log.debug("[JwtAuthenticationFilter] This token is access token");
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtService.isLogoutAccessToken(authorizationHeader)) {
            log.debug("[JwtAuthenticationFilter] Logout access token");
            filterChain.doFilter(request, response);
            return;
        }

        if (JwtUtil.isExpired(authorizationHeader)) {
            log.debug("[JwtAuthenticationFilter] Token is expired");
            filterChain.doFilter(request, response);
            return;
        }

        log.debug("[JwtAuthenticationFilter] Token is valid");
        Member loginMember = memberService.getMemberByEmail(
            JwtUtil.getLoginEmail(authorizationHeader));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginMember.getEmail(), loginMember.getPassword(),
            List.of(new SimpleGrantedAuthority(loginMember.getRole().toString())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

}