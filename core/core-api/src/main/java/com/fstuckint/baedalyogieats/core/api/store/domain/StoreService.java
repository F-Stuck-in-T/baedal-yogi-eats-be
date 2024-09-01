package com.fstuckint.baedalyogieats.core.api.store.domain;

import com.fstuckint.baedalyogieats.core.api.store.support.Cursor;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    private final StoreRegister storeRegister;

    private final StoreFinder storeFinder;

    private final StoreReader storeReader;

    public StoreService(StoreRegister storeRegister, StoreFinder storeFinder, StoreReader storeReader) {
        this.storeRegister = storeRegister;
        this.storeFinder = storeFinder;
        this.storeReader = storeReader;
    }

    public StoreResult register(OwnerStore ownerStore) {
        return storeRegister.register(ownerStore);
    }

    public StoreResult find(UUID storeUuid) {
        return storeFinder.find(storeUuid);
    }

    public List<StoreResult> read(Cursor cursor) {
        return storeReader.read(cursor);
    }

}
