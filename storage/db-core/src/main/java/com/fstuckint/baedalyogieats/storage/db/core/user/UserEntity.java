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
@Table(name = "users")
public class UserEntity extends BaseEntity {

<<<<<<< HEAD:storage/db-core/src/main/java/com/fstuckint/baedalyogieats/storage/db/core/user/User.java
    @Id
    @GeneratedValue
    private UUID uuid;

=======
>>>>>>> feature/tmp:storage/db-core/src/main/java/com/fstuckint/baedalyogieats/storage/db/core/user/UserEntity.java
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
<<<<<<< HEAD:storage/db-core/src/main/java/com/fstuckint/baedalyogieats/storage/db/core/user/User.java

    public User updatePassword(String password) {
        this.password = !password.isEmpty() ? password : this.password;
        return this;
=======
    public void updatePassword(String password) {
        this.password = password;
>>>>>>> feature/tmp:storage/db-core/src/main/java/com/fstuckint/baedalyogieats/storage/db/core/user/UserEntity.java
    }

    public UserEntity deleteUser() {
        isDeleted = true;
        return this;
    }

}
