package com.fstuckint.baedalyogieats.core.api.user.controller.v1.request;

import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import lombok.Data;
import lombok.Getter;

@Data
public class SignupDto {

    private String username;
    private String password;
    private String nickname;

    @Getter
    private UserRole userRole;

}
