package com.fstuckint.baedalyogieats.storage.db.core.customerreport;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerReportRepository extends JpaRepository<CustomerReportEntity, UUID> {

    Optional<CustomerReportEntity> findByUuidAndIsDeletedFalse(UUID uuid);

    List<CustomerReportEntity> findAllByIsDeletedFalse();

}
