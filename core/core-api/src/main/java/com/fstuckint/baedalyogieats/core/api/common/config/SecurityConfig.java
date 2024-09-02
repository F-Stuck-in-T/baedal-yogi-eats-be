package com.fstuckint.baedalyogieats.core.api.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.common.security.JwtAuthenticationFilter;
import com.fstuckint.baedalyogieats.core.api.common.security.JwtAuthorizationFilter;
import com.fstuckint.baedalyogieats.core.api.common.security.UserDetailsServiceImpl;
import com.fstuckint.baedalyogieats.core.api.user.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableJpaAuditing
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    private final AuthenticationConfiguration configuration;

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtils);
        filter.setAuthenticationManager(authenticationManager(configuration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(req -> req.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
            .permitAll()

            // USER
            .requestMatchers("/api/v1/users", "/api/v1/users/authorization")
            .permitAll()
            .requestMatchers("/api/v1/users/token")
            .hasAnyRole("CUSTOMER", "OWNER", "MANAGER", "MASTER")
            .requestMatchers(HttpMethod.GET, "/api/v1/users/**")
            .hasAnyRole("CUSTOMER", "OWNER", "MANAGER", "MASTER")
            .requestMatchers(HttpMethod.PUT, "/api/v1/users/**")
            .hasAnyRole("CUSTOMER", "MANAGER", "MASTER")
            .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**")
            .hasAnyRole("CUSTOMER", "MANAGER", "MASTER")

            // ADRESS
            .requestMatchers("/api/v1/address", "/api/v1/address/**", "/api/v1/address/users/**")
            .hasAnyRole("CUSTOMER", "MANAGER", "MASTER")
            .requestMatchers("/api/v1/address/admin")
            .hasAnyRole("MANAGER", "MASTER")

            // PAYMENT
            .requestMatchers("/api/v1/payments/users/**")
            .hasAnyRole("CUSTOMER", "MANAGER", "MASTER")
            .requestMatchers(HttpMethod.POST, "/api/v1/payments")
            .hasAnyRole("CUSTOMER", "MANAGER", "MASTER")
            .requestMatchers(HttpMethod.GET, "/api/v1/payment")
            .hasAnyRole("MANAGER", "MASTER")
            .requestMatchers(HttpMethod.DELETE, "/api/v1/payments/**")
            .hasAnyRole("MANAGER", "MASTER")
            .requestMatchers("/api/v1/payments/stores/**")
            .hasAnyRole("OWNER", "MANAGER", "MASTER")

            // AI
            .requestMatchers("/api/v1/ai/product/description")
            .hasAnyRole("OWNER", "MANAGER", "MASTER")

            // ANNOUNCEMENT
            .requestMatchers(HttpMethod.GET, "/api/v1/announcement/**")
            .hasAnyRole("CUSTOMER", "OWNER", "MANAGER", "MASTER")
            .requestMatchers(HttpMethod.GET, "/api/v1/announcement")
            .hasAnyRole("CUSTOMER", "OWNER", "MANAGER", "MASTER")
            .requestMatchers(HttpMethod.PUT, "/api/v1/announcement/**")
            .hasAnyRole("MANAGER", "MASTER")
            .requestMatchers(HttpMethod.POST, "/api/v1/announcement")
            .hasAnyRole("MANAGER", "MASTER")
            .requestMatchers(HttpMethod.DELETE, "/api/v1/announcement/**")
            .hasAnyRole("MANAGER", "MASTER")

            // CustomerReport
            .requestMatchers(HttpMethod.POST, "/api/v1/report")
            .hasAnyRole("CUSTOMER", "OWNER")
            .requestMatchers(HttpMethod.PUT, "/api/v1/report/**")
            .hasAnyRole("CUSTOMER", "OWNER")
            .requestMatchers(HttpMethod.POST, "/api/v1/report/**")
            .hasAnyRole("MANAGER", "MASTER")
            .requestMatchers(HttpMethod.GET, "/api/v1/report")
            .hasAnyRole("MANAGER", "MASTER")
            .requestMatchers("/api/v1/report/**")
            .hasAnyRole("CUSTOMER", "OWNER", "MANAGER", "MASTER")

            // 나머지 API 도 추후 완성되면 추가 예정 ( 완성 전까지 PermitAll() )

            .anyRequest()
            .permitAll());

        http.exceptionHandling(eh -> eh.accessDeniedHandler(((request, response, accessDeniedException) -> {
            log.error(accessDeniedException.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.setContentType("application/json");
            String jsonResponse = new ObjectMapper().writeValueAsString(ApiResponse.error(ErrorType.ROLE_ERROR));
            response.getWriter().write(jsonResponse);
            response.flushBuffer();
        })));

        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
    public WebSecurityCustomizer configuredH2ConsoleEnable() {
        return web -> web.ignoring().requestMatchers(PathRequest.toH2Console());
    }

}
