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

    public OrderEntity storeOrderAgg(OrderEntity initOrder, BuyerEntity initBuyer,
            List<OrderItemEntity> initOrderItems) {

        BuyerEntity buyer = buyerRepository.save(initBuyer);
        initOrder.addBuyer(initBuyer.getUuid());
        OrderEntity order = orderRepository.save(initOrder);
        List<OrderItemEntity> orderItem = orderItemRepository.saveAll(initOrderItems);
        order.addBuyer(buyer.getUuid());
        order.addTotalPrice(orderItem.stream().mapToInt(OrderItemEntity::getUnitPrice).sum());

        return order;
    }

}
