package com.fstuckint.baedalyogieats.core.api.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.LoginDto;
import com.fstuckint.baedalyogieats.core.api.user.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.user.support.error.UserException;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
        setFilterProcessesUrl("/api/v1/users/authorization");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            log.info("authentication filter run");

            LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);

            String username = loginDto.username();
            String password = loginDto.password();

            log.info("authentication filter username: {}", username);
            log.info("authentication filter password: {}", password);

            return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(username, password, null));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            try {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                String jsonResponse = new ObjectMapper()
                    .writeValueAsString(ApiResponse.error(ErrorType.BAD_REQUEST_ERROR));
                response.getWriter().write(jsonResponse);
                response.getWriter().flush();
            }
            catch (Exception ex) {
                log.error(ex.getMessage());
                throw new RuntimeException("Server Error");
            }
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UUID userUuid = ((UserDetailsImpl) authResult.getPrincipal()).getUserUuid();
        UserRole role = ((UserDetailsImpl) authResult.getPrincipal()).getUserEntity().getRole();

        String token = jwtUtils.createToken(userUuid, username, role);
        jwtUtils.setTokenInHeader(token, response);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(ApiResponse.success(token));
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        throw new UserException(ErrorType.BAD_REQUEST_ERROR);
    }

}