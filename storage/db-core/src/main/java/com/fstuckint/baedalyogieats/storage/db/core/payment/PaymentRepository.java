package com.fstuckint.baedalyogieats.storage.db.core.payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.*;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    @Query("select p from PaymentEntity p where p.isCancel = false and p.createdAt > :dateCursor")
    Page<PaymentEntity> findAllByAdmin(LocalDateTime dateCursor, Pageable sortedPage);

    @Query("select p from PaymentEntity p where p.isCancel = false and p.userUuid = :userUuid and p.createdAt > :dateCursor")
    Page<PaymentEntity> findAllByUserUuid(UUID userUuid, LocalDateTime dateCursor, Pageable sortedPage);

    @Query("select p from PaymentEntity p where p.isCancel = false and p.storeUuid = :storeUuid and p.createdAt > :dateCursor")
    Page<PaymentEntity> findAllByStoreUuid(UUID storeUuid, LocalDateTime dateCursor, Pageable sortedPage);

    Optional<PaymentEntity> findByUuidAndIsCancelFalse(UUID uuid);

    Optional<PaymentEntity> findByOrderUuid(UUID uuid);

}
