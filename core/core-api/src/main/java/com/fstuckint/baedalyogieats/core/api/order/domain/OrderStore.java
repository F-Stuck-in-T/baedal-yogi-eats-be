package com.fstuckint.baedalyogieats.core.api.order.domain;

import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@RequiredArgsConstructor
public class OrderStore {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final BuyerRepository buyerRepository;

    public OrderEntity storeOrderAgg(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntities,
            BuyerEntity buyerEntity) {

        OrderEntity savedOrder = orderRepository.save(orderEntity);
        BuyerEntity savedBuyer = buyerRepository.save(buyerEntity);
        savedOrder.addBuyer(savedBuyer.getUuid());
        orderItemRepository.saveAll(orderItemEntities);

        return savedOrder;
    }

}
