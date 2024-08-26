package com.fstuckint.baedalyogieats.storage.db.core.user;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String nickname);

    @Query("select u from User u where u.createdAt > :dateCursor")
    Page<User> findAllByCursor(LocalDateTime dateCursor, PageRequest sortedPage);

    Boolean existsByCreatedAtLessThan(LocalDateTime dateTime);

}
