package com.fstuckint.baedalyogieats.storage.db.core.address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {

    List<AddressEntity> findAllByUsername(String username);

    List<AddressEntity> findByUserUuidAndIsDeletedFalse(UUID userUuid);

    List<AddressEntity> findAllByUsernameAndIsDeletedFalse(String username);
    List<AddressEntity> findAllByUuidAndIsDeletedFalse(UUID uuid);

    Optional<AddressEntity> findByUuidAndIsDeletedFalse(UUID uuid);
    Optional<AddressEntity> findByUsernameAndIsDeletedFalse(String username);
}
