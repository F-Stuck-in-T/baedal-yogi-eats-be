package com.fstuckint.baedalyogieats.storage.db.core.announcement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, UUID> {

    Optional<AnnouncementEntity> findByUuidAndIsDeletedFalse(UUID uuid);

    @Query("select a from AnnouncementEntity a where a.createdAt > :dateTime and a.isDeleted = false")
    Page<AnnouncementEntity> findAll(LocalDateTime dateTime, Pageable pageRequest);

}
