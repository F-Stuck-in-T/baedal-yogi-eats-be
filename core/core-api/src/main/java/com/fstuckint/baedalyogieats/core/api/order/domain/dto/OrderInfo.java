package com.fstuckint.baedalyogieats.core.api.order.domain.dto;

import com.fstuckint.baedalyogieats.core.enums.order.*;
import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Getter
@Builder
public class OrderInfo {

    private UUID uuid;

    private OrderType orderType;

    private OrderStatus orderStatus;

    private UUID buyerUuid;

    private UUID storeUuid;

    private Integer totalPrice;

    private LocalDateTime createdAT;

    public static OrderInfo of(OrderEntity order) {
        return OrderInfo.builder()
            .uuid(order.getUuid())
            .orderType(order.getOrderType())
            .orderStatus(order.getOrderStatus())
            .buyerUuid(order.getBuyerUuid())
            .storeUuid(order.getStoreUuid())
            .totalPrice(order.getTotalPrice())
            .createdAT(order.getCreatedAt())
            .build();
    }

}
