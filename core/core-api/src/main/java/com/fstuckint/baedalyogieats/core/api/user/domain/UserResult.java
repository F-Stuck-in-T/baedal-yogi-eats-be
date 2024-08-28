package com.fstuckint.baedalyogieats.core.api.user.domain;

import com.fstuckint.baedalyogieats.core.api.user.controller.v1.response.UserPageResponse;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserEntity;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public record UserResult(UUID uuid, String username, String password, String nickname, UserRole userRole) {

    public static UserResult of(UserEntity userEntity) {
        if (userEntity == null)
            return null;
        return new UserResult(userEntity.getUuid(), userEntity.getUsername(), userEntity.getPassword(),
                userEntity.getNickname(), userEntity.getRole());
    }

    public static UserPageResponse toUserPageResponse(Page<UserEntity> userPage) {
        List<UserResult> list = userPage.stream().map(UserResult::of).toList();
        boolean hasNext = userPage.hasNext();
        return new UserPageResponse(list, hasNext);
    }
}
