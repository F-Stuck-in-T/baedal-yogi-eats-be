package com.fstuckint.baedalyogieats.storage.db.core.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByNickname(String nickname);

    @Query("select u from UserEntity u where u.isDeleted = false and u.createdAt > :dateCursor")
    Page<UserEntity> findAllUserByIsDeletedFalse(LocalDateTime dateCursor, Pageable pageable);

<<<<<<< HEAD
    @Query("select u from User u where u.isDeleted = false and u.createdAt > :dateCursor")
    Page<User> findAllByCursor(LocalDateTime dateCursor, PageRequest sortedPage);

    Optional<User> findByUsernameAndIsDeletedFalse(String username);

    Optional<User> findByUuidAndIsDeletedFalse(UUID uuid);
=======
    Optional<UserEntity> findByUsernameAndIsDeletedFalse(String username);
    Optional<UserEntity> findByNicknameAndIsDeletedFalse(String nickname);
    Optional<UserEntity> findByUuidAndIsDeletedFalse(UUID uuid);
>>>>>>> feature/tmp

}
