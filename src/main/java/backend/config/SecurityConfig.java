package backend.config;

import backend.jwt.JwtAccessDeniedHandler;
import backend.jwt.JwtAuthenticationEntryPoint;
import backend.jwt.JwtFilter;
import backend.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(tokenProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1) CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // 2) CORS 필터를 UsernamePasswordAuthenticationFilter 전에 등록
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                // 3) 예외 처리 핸들러 설정
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                // 4) H2 콘솔 혹은 iframe 허용을 위해 sameOrigin
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )

                // 5) 세션 STATLESS 설정
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 6) 요청별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // ① 정적 리소스
                        .requestMatchers(
                                "/signup.html",
                                "/login.html"
                        ).permitAll()

                        // ② 인증 없이 허용할 API
                        .requestMatchers(
                                "/api/auth/signup",
                                "/api/auth/login",
                                "/api/auth/reissue",
                                "/api/auth/logout"
                        ).permitAll()

                        // ③ 그 외 모든 요청(여기엔 /api/auth/profile 포함) 은 인증 필요
                        .anyRequest().authenticated()
                );

        // 7) JwtFilter를 UsernamePasswordAuthenticationFilter 전에 등록
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
