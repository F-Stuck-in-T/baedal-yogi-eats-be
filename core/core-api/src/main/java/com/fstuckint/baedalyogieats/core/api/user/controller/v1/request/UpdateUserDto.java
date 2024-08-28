package com.fstuckint.baedalyogieats.core.api.user.controller.v1.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserDto(@Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters.")
                            @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$")
                            String password,
                            String nickname) {


}
