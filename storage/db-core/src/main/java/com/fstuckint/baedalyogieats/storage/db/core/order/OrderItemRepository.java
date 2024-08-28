package com.fstuckint.baedalyogieats.storage.db.core.order;

import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {

    default OrderItemEntity add(OrderItemEntity orderItemEntity) {
        return save(orderItemEntity);
    }

}
