package com.fstuckint.baedalyogieats.core.api.common.jwt;

import com.fstuckint.baedalyogieats.core.api.user.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.user.support.error.UserException;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserChecker {

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public boolean checkPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    public void checkTokenValid(String token) {
        if (token == null || !jwtUtils.validationToken(token))
            throw new UserException(ErrorType.TOKEN_ERROR);
    }

    public void checkIdentityByUserUuid(String token, UUID id) {
        UUID uuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        if (!uuid.equals(id))
            throw new UserException(ErrorType.TOKEN_ERROR);
    }

    public boolean checkAdmin(String token) {
        String role = jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_ROLE).toString();
        return role.equals(UserRole.MANAGER.getAuthority()) || role.equals(UserRole.MASTER.getAuthority());
    }

    public void checkTokenNotOwner(String token) {
        String role = jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_ROLE).toString();
        if (UserRole.OWNER.getAuthority().equals(role))
            throw new UserException(ErrorType.ROLE_ERROR);
    }

    public boolean checkTokenOwner(String token) {
        String role = jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_ROLE).toString();
        return UserRole.OWNER.getAuthority().equals(role);
    }

    public void checkIdentityByUsername(String token, String username) {
        UUID uuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
    }
  
}
