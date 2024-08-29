package com.fstuckint.baedalyogieats.storage.db.core.store;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreEntity, UUID> {

    default StoreEntity add(StoreEntity storeEntity) {
        return save(storeEntity);
    }

    Optional<StoreEntity> findByUuid(UUID uuid);

}
