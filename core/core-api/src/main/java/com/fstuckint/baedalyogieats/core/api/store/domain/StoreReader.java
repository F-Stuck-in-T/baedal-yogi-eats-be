package com.fstuckint.baedalyogieats.core.api.store.domain;

import com.fstuckint.baedalyogieats.core.api.store.support.Cursor;
import com.fstuckint.baedalyogieats.storage.db.core.store.StoreRepository;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StoreReader {

    private final StoreRepository storeRepository;

    public StoreReader(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional(readOnly = true)
    public List<StoreResult> read(Cursor cursor) {
        return storeRepository
            .findByCursor(cursor.getUuid(), cursor.getTimestamp(), cursor.limit() + 1, cursor.sortKey(), cursor.sort())
            .stream()
            .map(StoreResult::of)
            .toList();
    }

}
