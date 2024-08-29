package com.fstuckint.baedalyogieats.storage.db.core.product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;

public interface ProductRepositoryCustom {

    List<ProductEntity> findByCursor(UUID lastUuid, LocalDateTime lastTimestamp, long limit, String sortKey,
            Sort.Direction sort);

}
