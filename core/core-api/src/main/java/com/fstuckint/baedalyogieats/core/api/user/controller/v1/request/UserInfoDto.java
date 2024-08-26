package com.fstuckint.baedalyogieats.core.api.user.controller.v1.request;

import com.fstuckint.baedalyogieats.storage.db.core.user.User;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import lombok.Data;

@Data
public class UserInfoDto {

    private Long userId;
    private String nickname;
    private String userRole;

    public UserInfoDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.userRole = user.getRole().getAuthority();
    }
}
