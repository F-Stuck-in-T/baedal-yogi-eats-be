package com.fstuckint.baedalyogieats.core.api.order.domain;

import com.fstuckint.baedalyogieats.core.api.order.domain.dto.*;
import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderStore orderStore;

    @Transactional
    public OrderInfo register(RegisterOrderCommand command) {

        OrderEntity orderEntity = OrderEntity.builder()
            .type(command.getType())
            .buyerUuid(command.getBuyer().getUserUuid())
            .build();

        List<OrderItemEntity> orderItemEntities = command.getProducts()
            .stream()
            .map(product -> OrderItemEntity.builder()
                .name(product.getName())
                .unitPrice(product.getUnitPrice())
                .orderEntity(orderEntity)
                .productUuid(product.getProductUuid())
                .build())
            .toList();

        orderEntity.setTotalPrice(orderItemEntities.stream().mapToInt(OrderItemEntity::getUnitPrice).sum());

        BuyerEntity buyerEntity = BuyerEntity.builder()
            .nickname(command.getBuyer().getNickname())
            .userUuid(command.getBuyer().getUserUuid())
            .build();

        OrderEntity savedOrder = orderStore.storeOrder(orderEntity, orderItemEntities, buyerEntity);

        return new OrderInfo(savedOrder);
    }

}
