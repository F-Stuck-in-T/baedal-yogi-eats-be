package com.fstuckint.baedalyogieats.core.api.order.controller.v1.request;

import com.fstuckint.baedalyogieats.core.api.order.domain.dto.*;
import com.fstuckint.baedalyogieats.core.api.order.domain.dto.RegisterOrderCommand.*;
import com.fstuckint.baedalyogieats.core.enums.order.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Getter
@NoArgsConstructor
public class RegisterOrderRequest implements Serializable {

    private OrderType orderType;

    private RegisterBuyerRequest buyer;

    private UUID storeUuid;

    private List<RegisterOrderItemRequest> orderItems = new ArrayList<>();

    public RegisterOrderCommand toCommand() {

        RegisterBuyerCommand buyer = this.buyer.toCommand();

        List<RegisterOrderItemCommand> orderItems = this.orderItems.stream()
            .map(RegisterOrderItemRequest::toCommand)
            .toList();

        return RegisterOrderCommand.builder()
            .orderType(this.orderType)
            .buyer(buyer)
            .storeUuid(this.storeUuid)
            .orderItems(orderItems)
            .build();
    }

    @Getter
    @NoArgsConstructor
    public static class RegisterBuyerRequest {

        private String nickname;

        private UUID userUuid;

        public RegisterBuyerCommand toCommand() {
            return RegisterBuyerCommand.builder().nickname(this.nickname).userUuid(this.userUuid).build();
        }

    }

    @Getter
    @NoArgsConstructor
    public static class RegisterOrderItemRequest {

        private String name;

        private Integer unitPrice;

        private UUID productUuid;

        public RegisterOrderItemCommand toCommand() {
            return RegisterOrderItemCommand.builder()
                .name(this.name)
                .unitPrice(this.unitPrice)
                .productUuid(this.productUuid)
                .build();
        }

    }

}
