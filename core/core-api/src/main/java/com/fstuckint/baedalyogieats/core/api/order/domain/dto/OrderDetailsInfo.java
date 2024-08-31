package com.fstuckint.baedalyogieats.core.api.order.domain.dto;

import com.fstuckint.baedalyogieats.core.enums.order.*;
import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Getter
@Builder
public class OrderDetailsInfo {

    private UUID orderUuid;
    private Type type;
    private Status status;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private BuyerInfo buyer;
    private List<OrderItemInfo> orderItems;

    public static OrderDetailsInfo of(OrderEntity orderEntity, BuyerEntity buyer, List<OrderItemEntity> orderItems) {
        List<OrderItemInfo> orderItemInfos = orderItems.stream().map(OrderItemInfo::of).toList();

        return OrderDetailsInfo.builder()
                .orderUuid(orderEntity.getUuid())
                .type(orderEntity.getType())
                .status(orderEntity.getStatus())
                .totalPrice(orderEntity.getTotalPrice())
                .createdAt(orderEntity.getCreatedAt())
                .buyer(BuyerInfo.of(buyer))
                .orderItems(orderItemInfos)
                .build();
    }

}
