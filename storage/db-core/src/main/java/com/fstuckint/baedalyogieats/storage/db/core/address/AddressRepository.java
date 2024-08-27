package com.fstuckint.baedalyogieats.storage.db.core.address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> findAllByUsername(String username);

    List<Address> findAllByUsernameAndIsDeletedFalse(String username);

    List<Address> findAllByUuidAndIsDeletedFalse(UUID uuid);

    Optional<Address> findByUuidAndIsDeletedFalse(UUID uuid);

    Optional<Address> findByUsernameAndIsDeletedFalse(String username);

}
