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

        OrderEntity orderEntity = command.toEntity();

        List<OrderItemEntity> orderItemEntities = command.getOrderItems()
            .stream()
            .map(orderItem -> orderItem.toEntity(orderEntity))
            .toList();

        BuyerEntity buyerEntity = command.getBuyer().toEntity();

        OrderEntity storedOrder = orderStore.storeOrderAgg(orderEntity, orderItemEntities, buyerEntity);
        storedOrder.addBuyer(buyerEntity.getUuid());
        storedOrder.addTotalPrice(orderItemEntities.stream().mapToInt(OrderItemEntity::getUnitPrice).sum());

        return new OrderInfo(storedOrder);
    }

}
