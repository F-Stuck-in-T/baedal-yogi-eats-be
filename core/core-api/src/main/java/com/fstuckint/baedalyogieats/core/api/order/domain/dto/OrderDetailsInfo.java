package com.fstuckint.baedalyogieats.core.api.order.domain.dto;

import com.fstuckint.baedalyogieats.core.api.payment.domain.*;
import com.fstuckint.baedalyogieats.core.enums.order.*;
import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Getter
@Builder
public class OrderDetailsInfo {

    private UUID orderUuid;

    private OrderType orderType;

    private OrderStatus orderStatus;

    private Integer totalPrice;

    private LocalDateTime createdAt;

    private BuyerInfo buyer;

    private List<OrderItemInfo> orderItems;

    private PaymentInfo payment;

    public static OrderDetailsInfo of(OrderEntity order, BuyerEntity buyer, List<OrderItemEntity> orderItems) {
        List<OrderItemInfo> orderItemInfos = orderItems.stream().map(OrderItemInfo::of).toList();

        return OrderDetailsInfo.builder()
            .orderUuid(order.getUuid())
            .orderType(order.getOrderType())
            .orderStatus(order.getOrderStatus())
            .totalPrice(order.getTotalPrice())
            .createdAt(order.getCreatedAt())
            .buyer(BuyerInfo.of(buyer))
            .orderItems(orderItemInfos)
            .build();
    }

    public void addPaymentInfo(PaymentInfo payment) {
        this.payment = payment;
    }

}
