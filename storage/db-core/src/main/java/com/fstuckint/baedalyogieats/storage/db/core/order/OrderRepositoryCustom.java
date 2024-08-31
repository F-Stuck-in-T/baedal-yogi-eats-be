package com.fstuckint.baedalyogieats.storage.db.core.order;

import org.springframework.data.domain.*;

import java.util.*;

public interface OrderRepositoryCustom {
    List<OrderEntity> findByBuyer(UUID userId, Pageable pageable);
}
