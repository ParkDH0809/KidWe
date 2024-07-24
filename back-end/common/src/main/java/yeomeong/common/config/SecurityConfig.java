package yeomeong.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
import yeomeong.common.security.*;
import yeomeong.common.service.MemberService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MemberService memberService) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(config -> config
                        .successHandler(loginSuccessHandler))
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(HttpMethod.POST, "/login", "/join", "/").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(memberService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() {
        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter = new JsonUsernamePasswordAuthenticationFilter(objectMapper, loginSuccessHandler, loginFailureHandler);
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