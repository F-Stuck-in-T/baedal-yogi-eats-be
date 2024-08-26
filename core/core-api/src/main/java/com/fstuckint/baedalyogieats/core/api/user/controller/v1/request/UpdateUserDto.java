package com.fstuckint.baedalyogieats.core.api.user.controller.v1.request;

import lombok.Data;

@Data
public class UpdateUserDto {

    private String password;
    private String nickname;
}
