package com.fstuckint.baedalyogieats.storage.db.core.user;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByNickname(String nickname);

    @Query("select u from User u where u.isDeleted = false and u.createdAt > :dateCursor")
    Page<User> findAllByCursor(LocalDateTime dateCursor, PageRequest sortedPage);

    Optional<User> findByUsernameAndIsDeletedFalse(String username);
    Optional<User> findByUuidAndIsDeletedFalse(UUID uuid);



}
