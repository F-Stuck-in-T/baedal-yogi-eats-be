package com.fstuckint.baedalyogieats.core.api.store.domain;

import com.fstuckint.baedalyogieats.storage.db.core.store.StoreRepository;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class StoreFinder {

    private final StoreRepository storeRepository;

    public StoreFinder(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public StoreResult find(UUID storeUuid) {
        return StoreResult.of(storeRepository.findByUuid(storeUuid).orElseThrow());
    }

}
