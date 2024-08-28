package com.fstuckint.baedalyogieats.core.api.order.domain.dto;

import com.fstuckint.baedalyogieats.core.enums.order.*;
import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import lombok.*;

import java.util.*;

@Getter
public class OrderInfo {

    private UUID uuid;

    private Type type;

    private Status status;

    private UUID buyerUuid;

    private Integer totalPrice;

    public OrderInfo(OrderEntity entity) {
        this.uuid = entity.getUuid();
        this.type = entity.getType();
        this.status = entity.getStatus();
        this.buyerUuid = entity.getBuyerUuid();
        this.totalPrice = entity.getTotalPrice();
    }

}
