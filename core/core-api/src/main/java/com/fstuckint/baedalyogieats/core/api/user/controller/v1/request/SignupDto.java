package com.fstuckint.baedalyogieats.core.api.user.controller.v1.request;

import com.fstuckint.baedalyogieats.core.api.user.domain.User;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record SignupDto(
        @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters.") @Pattern(
                regexp = "^[a-z0-9]+$") String username,
        @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters.") @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$") String password,
        String nickname, UserRole userRole) {

    public User toUser() {
        return new User(username, password, nickname, userRole);
    }

}
