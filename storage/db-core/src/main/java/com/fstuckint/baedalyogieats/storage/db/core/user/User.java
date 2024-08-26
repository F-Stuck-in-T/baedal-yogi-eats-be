package com.fstuckint.baedalyogieats.storage.db.core.user;

import com.fstuckint.baedalyogieats.storage.db.core.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
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

    private LocalDateTime deletedAt;

    @Column(length = 100)
    private String deletedBy;
}
