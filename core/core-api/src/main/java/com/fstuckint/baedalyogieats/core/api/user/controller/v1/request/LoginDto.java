package com.fstuckint.baedalyogieats.core.api.user.controller.v1.request;

import lombok.Data;

@Data
public class LoginDto {

    private String username;
    private String password;
}
