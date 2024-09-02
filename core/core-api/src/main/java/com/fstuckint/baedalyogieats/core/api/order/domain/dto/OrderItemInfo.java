package com.fstuckint.baedalyogieats.core.api.order.domain.dto;

import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import lombok.*;

import java.util.*;

@Getter
@Builder
public class OrderItemInfo {

    private String name;

    private Integer unitPrice;

    private UUID productUuid;

    public static OrderItemInfo of(OrderItemEntity orderItemEntity) {
        return OrderItemInfo.builder()
            .name(orderItemEntity.getName())
            .unitPrice(orderItemEntity.getUnitPrice())
            .productUuid(orderItemEntity.getProductUuid())
            .build();
    }

}
