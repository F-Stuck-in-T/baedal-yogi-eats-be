package com.fstuckint.baedalyogieats.storage.db.core.address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {

    List<AddressEntity> findByUserUuidAndIsDeletedFalse(UUID userUuid);

    Optional<AddressEntity> findByUuidAndIsDeletedFalse(UUID uuid);

}
