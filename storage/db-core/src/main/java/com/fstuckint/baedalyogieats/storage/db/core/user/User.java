package com.fstuckint.baedalyogieats.storage.db.core.user;

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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID uuid;

    private String username;

    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String username, String password, String nickname, UserRole role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @Column(length = 100)
    private String createdBy;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @Column(length = 100)
    private String updatedBy;

    private boolean isDeleted = false;

    public User updateNickname(String nickname) {
        this.nickname = !nickname.isEmpty() ? nickname : this.nickname;
        return this;
    }

    public User updatePassword(String password) {
        this.password = !password.isEmpty() ? password : this.password;
        return this;
    }

    public User deleteUser() {
        isDeleted = true;
        return this;
    }

}
