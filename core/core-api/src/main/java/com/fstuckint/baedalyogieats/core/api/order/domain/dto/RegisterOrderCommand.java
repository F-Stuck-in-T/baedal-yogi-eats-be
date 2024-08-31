package com.fstuckint.baedalyogieats.core.api.order.domain.dto;

import com.fstuckint.baedalyogieats.core.enums.order.*;
import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import lombok.*;

import java.util.*;

@Getter
@Builder
@AllArgsConstructor
public class RegisterOrderCommand {

    private OrderType orderType;

    private RegisterBuyerCommand buyer;

    private UUID storeUuid;

    private List<RegisterOrderItemCommand> orderItems = new ArrayList<>();

    public OrderEntity toEntity() {
        return OrderEntity.builder().orderType(this.orderType).storeUuid(this.storeUuid).build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RegisterBuyerCommand {

        private String nickname;

        private UUID userUuid;

        public BuyerEntity toEntity() {
            return BuyerEntity.builder().nickname(this.nickname).userUuid(this.userUuid).build();
        }

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RegisterOrderItemCommand {

        private String name;

        private Integer unitPrice;

        private UUID productUuid;

        public OrderItemEntity toEntity(OrderEntity orderEntity) {
            return OrderItemEntity.builder()
                .name(this.name)
                .unitPrice(this.unitPrice)
                .orderEntity(orderEntity)
                .productUuid(this.productUuid)
                .build();
        }

    }

}
