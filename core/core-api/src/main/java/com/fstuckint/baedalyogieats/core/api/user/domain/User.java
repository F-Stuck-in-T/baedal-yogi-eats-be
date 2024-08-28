package com.fstuckint.baedalyogieats.core.api.user.domain;

import com.fstuckint.baedalyogieats.storage.db.core.user.UserEntity;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String username;

    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserEntity toEntity() {
        return new UserEntity(username, password, nickname, role);
    }

    public void encodingPassword(String encodedPassword) {
        password = encodedPassword;
    }

}
