package yeomeong.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import yeomeong.common.security.*;
import yeomeong.common.security.jwt.JwtService;
import yeomeong.common.security.jwt.JwtUtil;
import yeomeong.common.service.MemberService;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {

    private final ObjectMapper objectMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationProviderImpl authenticationProvider;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MemberService memberService, JwtService jwtService) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .formLogin(config -> config
                        .successHandler(loginSuccessHandler));

        http
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/*", "/api-docs", "/api-docs/*", "/v3/api-docs/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/login", "/signup", "/refresh").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                                .anyRequest().permitAll()

//                                // swagger
//                                .requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/*", "/api-docs", "/api-docs/*", "/v3/api-docs/*").permitAll()
//                                // 로그인, 회원가입 API
//                                .requestMatchers(HttpMethod.POST, "/login", "/signup").permitAll()
//                                .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
//                                // 게시판 댓글 API
//                                // 공지사항 API
//                                .requestMatchers( "/announcements/*", "/announcements/storage/**", "/announcements/vote/*").hasAnyRole("DIRECTOR", "TEACHER")
//                                // 귀가동의서 API
//                                .requestMatchers(HttpMethod.POST,"/leaveconsents/*").hasRole("GUARDIAN")
//                                .requestMatchers(HttpMethod.DELETE,"/leaveconsents/*").hasRole("GUARDIAN")
//                                // 반 API
//                                // 부모 권한 API
//                                .requestMatchers("/guardians/**").hasRole("GUARDIAN")
//                                // 사용자 API
//                                // 선생 권한 API
//                                .requestMatchers("/teachers/**").hasRole("TEACHER")
//                                // 식단 API
//                                .requestMatchers("/menus/*").hasAnyRole("DIRECTOR", "TEACHER")
//                                // 아이 API
//                                // 알림 API
//                                // 알림장 댓글 API
//                                // 알림장 API
//                                .requestMatchers("/dailynotes/*/*", "/dailynotes/ban/**").hasAnyRole("DIRECTOR", "TEACHER")
//                                .requestMatchers("/dailynotes/kid/**").hasRole("GUARDIAN")
//                                // 원장 권한 API
//                                .requestMatchers("/directors/**").hasRole("DIRECTOR")
//                                // 유치원 API
//                                // 일정 관리 API
//                                .requestMatchers("/schedules/*").hasAnyRole("DIRECTOR", "TEACHER")
//                                //출석 API
//                                .requestMatchers("/attendances").hasAnyRole("DIRECTOR", "TEACHER")
//                                //투약의뢰서 API
//                                .requestMatchers(HttpMethod.POST,"/medications/*").hasRole("GUARDIAN")
//                                .requestMatchers(HttpMethod.DELETE,"/medications/*").hasRole("GUARDIAN")
//                                .requestMatchers("/medications/ban/**").hasAnyRole("DIRECTOR", "TEACHER")
//                                .requestMatchers("/medications/kid/**").hasRole("GUARDIAN")
//
//                                .anyRequest().authenticated()
                );

        http
            .addFilterBefore(jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthenticationFilter(memberService, jwtService, userDetailsService), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);

        http
                .exceptionHandling((exceptionHandling) ->
                    exceptionHandling.accessDeniedHandler(jwtAccessDeniedHandler)
                );

        http
                .logout((logout) -> logout
                        .permitAll()
                        .logoutSuccessHandler(((request, response, authentication) -> {
                            memberService.deleteNotificationToken(request.getHeader("Authorization"));
                            jwtService.saveLogoutAccessToken(request.getHeader("Authorization"));
                            jwtService.deleteRefreshToken(JwtUtil.getLoginEmail(request.getHeader("Authorization")));
                        }))
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() {
        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter
                = new JsonUsernamePasswordAuthenticationFilter(objectMapper, loginSuccessHandler, loginFailureHandler, authenticationProvider);
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        return jsonUsernamePasswordAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new PasswordEncoderConfig().passwordEncoder());
        return new ProviderManager(provider);
    }

}