package com.fstuckint.baedalyogieats.storage.db.core.user;

import com.fstuckint.baedalyogieats.storage.db.core.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_users")
public class UserEntity extends BaseEntity {

    private String username;

    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserEntity(String username, String password, String nickname, UserRole role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    private boolean isDeleted = false;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public UserEntity deleteUser() {
        isDeleted = true;
        return this;
    }

}
