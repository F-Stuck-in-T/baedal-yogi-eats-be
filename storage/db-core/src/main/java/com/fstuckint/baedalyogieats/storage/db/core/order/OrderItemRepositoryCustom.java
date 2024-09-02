package com.fstuckint.baedalyogieats.storage.db.core.order;

import org.springframework.data.domain.*;

import java.util.*;

public interface OrderItemRepositoryCustom {

    List<OrderItemEntity> findByOrderUuid(UUID orderUuid);

}
