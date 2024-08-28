package com.fstuckint.baedalyogieats.core.api.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.user.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("authorization filter run");
        String token = jwtUtils.extractToken(request);
        if (StringUtils.hasText(token)) {
            if (!jwtUtils.validationToken(token)) {
                log.error("Not Valid Token");
                handleAuthException(response);
                return;
            }
            log.info("authorization filter token valid!");
            Claims claims = jwtUtils.extractClaims(token);
            try {
                setAuthentication(claims.get(JwtUtils.CLAIMS_USERNAME).toString());
                log.info("authorization filter Authentication Object Create!");
            }
            catch (Exception e) {
                log.error(e.getMessage());
                handleServerException(response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String username) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    private Authentication createAuthentication(String username) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private void handleAuthException(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(400);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(ApiResponse.error(ErrorType.TOKEN_ERROR));
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

    private void handleServerException(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(400);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(ApiResponse.error(ErrorType.DEFAULTS_ERROR));
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

}
