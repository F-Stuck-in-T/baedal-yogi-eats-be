package com.fstuckint.baedalyogieats.core.api.store.domain;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    private final StoreRegister storeRegister;

    private final StoreFinder storeFinder;

    public StoreService(StoreRegister storeRegister, StoreFinder storeFinder) {
        this.storeRegister = storeRegister;
        this.storeFinder = storeFinder;
    }

    public StoreResult register(OwnerStore ownerStore) {
        return storeRegister.register(ownerStore);
    }

    public StoreResult find(UUID storeUuid) {
        return storeFinder.find(storeUuid);
    }

}
