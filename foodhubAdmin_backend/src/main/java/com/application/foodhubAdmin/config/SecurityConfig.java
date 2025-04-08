package com.application.foodhubAdmin.config;

import com.application.foodhubAdmin.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
//@EnableMethodSecurity // 메서드 보안 활성화 > @PreAuthorize("hasRole('ROLE_ADMIN')")
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    // 인증이 필요없는 URL 목록
    private final String[] permitList = {
            "/api/admin/user/logIn"           // 로그인
    };


    // 인증이 필요한 URL 목록
    private final String[] authenticateList = {
            "/api/admin/**"               // 관리자
    };

    // CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/admin/**", config);
        return source;
    }

    // 비밀번호 암호화 (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Spring Security 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults())  // CORS 설정 활성화 추가
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화 (JWT는 상태를 저장하지 않음 > 필요없음)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션사용 안 함
                // URL접근권한 설정 권한 설정
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(permitList).permitAll() // 인증없이 접근가능
                    .requestMatchers(authenticateList).authenticated() // 인증 필요
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // 관리자 권한필요(ROLE_ADMIN) > 403오류
                        .anyRequest().permitAll()) // 나머지 요청은 인증없이 접근가능
                // 예외 처리
                .exceptionHandling(exception -> exception
                .authenticationEntryPoint((req, res, ex) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication required")) // 인증 실패
                .accessDeniedHandler((req, res, ex) -> res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied"))) // 권한 부족
            .addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class); // JwtAuthFilter를 기존필터 UsernamePasswordAuthenticationFilter전에 추가해 JWT로 먼저 인증시도
        return http.build(); // 완성된 보안규칙 반환
    }

    // 인증 매니저 (로그인 시 사용)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // JWT 필터 추가 (해당 필터가 요청마다 토큰을 확인)
    @Bean
    public JwtAuthFilter securityFilter() {
        return new JwtAuthFilter(jwtUtil, userDetailsService);
    }

}