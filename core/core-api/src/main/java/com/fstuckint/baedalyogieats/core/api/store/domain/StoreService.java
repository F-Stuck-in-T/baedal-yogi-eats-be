package com.fstuckint.baedalyogieats.core.api.store.domain;

import org.springframework.stereotype.Service;

@Service
public class StoreService {

    private final StoreRegister storeRegister;

    public StoreService(StoreRegister storeRegister) {
        this.storeRegister = storeRegister;
    }

    public StoreResult register(OwnerStore ownerStore) {
        return storeRegister.register(ownerStore);
    }

}
