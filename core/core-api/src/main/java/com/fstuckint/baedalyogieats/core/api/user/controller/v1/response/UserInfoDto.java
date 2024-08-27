package com.fstuckint.baedalyogieats.core.api.user.controller.v1.response;

import com.fstuckint.baedalyogieats.storage.db.core.user.User;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import lombok.Data;

import java.util.UUID;

@Data
public class UserInfoDto {

    private UUID userId;

    private String nickname;

    private String userRole;

    public UserInfoDto(User user) {
        this.userId = user.getUuid();
        this.nickname = user.getNickname();
        this.userRole = user.getRole().getAuthority();
    }

}
