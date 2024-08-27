package com.fstuckint.baedalyogieats.core.api.user.controller.v1.response;

import com.fstuckint.baedalyogieats.core.api.user.domain.UserResult;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;

import java.util.UUID;

public record UserResponse(UUID uuid, String nickname, UserRole userRole) {

    public static UserResponse of(UserResult userResult) {
        return new UserResponse(userResult.uuid(), userResult.nickname(), userResult.userRole());
    }
}
