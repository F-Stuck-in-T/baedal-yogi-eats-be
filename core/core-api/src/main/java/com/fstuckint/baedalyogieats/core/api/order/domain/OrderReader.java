package com.fstuckint.baedalyogieats.core.api.order.domain;

import com.fstuckint.baedalyogieats.core.api.order.domain.dto.*;
import com.fstuckint.baedalyogieats.core.api.order.support.error.*;
import com.fstuckint.baedalyogieats.storage.db.core.*;
import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Component
@RequiredArgsConstructor
public class OrderReader {

    private final OrderRepository orderRepository;

    private final BuyerRepository buyerRepository;

    private final OrderItemRepository orderItemRepository;

    public OrderEntity getByUuid(UUID uuid) {
        return orderRepository.findById(uuid).orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND_ENTITY));
    }

    public OrderDetailsInfo getOrderAgg(UUID uuid) {
        OrderEntity order = orderRepository.findById(uuid)
            .orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND_ENTITY));
        BuyerEntity buyer = buyerRepository.findById(order.getBuyerUuid())
            .orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND_ENTITY));
        List<OrderItemEntity> orderItems = orderItemRepository.findByOrderUuid(order.getUuid());
        return OrderDetailsInfo.of(order, buyer, orderItems);
    }

    public List<OrderDetailsInfo> findOrderAgg(Pageable pageable) {
        Page<OrderEntity> orderPages = orderRepository.findAll(pageable);
        List<OrderEntity> orders = orderPages.getContent();
        Map<UUID, BuyerEntity> buyerMap = orders.stream()
            .collect(Collectors.toMap(BaseEntity::getUuid, order -> buyerRepository.findById(order.getBuyerUuid())
                .orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND_ENTITY))));
        Map<UUID, List<OrderItemEntity>> orderItemsMap = orders.stream()
            .collect(Collectors.toMap(OrderEntity::getUuid,
                    orderEntity -> orderItemRepository.findByOrderUuid(orderEntity.getUuid())));
        return orderPages.stream()
            .map(order -> OrderDetailsInfo.of(order, buyerMap.get(order.getUuid()), orderItemsMap.get(order.getUuid())))
            .toList();
    }

    public List<OrderDetailsInfo> findOrderAggByUser(UUID userId, Pageable pageable) {
        List<OrderEntity> orders = orderRepository.findByBuyer(userId, pageable);
        Map<UUID, BuyerEntity> buyerMap = orders.stream()
            .collect(Collectors.toMap(BaseEntity::getUuid, order -> buyerRepository.findById(order.getBuyerUuid())
                .orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND_ENTITY))));
        Map<UUID, List<OrderItemEntity>> orderItemsMap = orders.stream()
            .collect(Collectors.toMap(OrderEntity::getUuid,
                    order -> orderItemRepository.findByOrderUuid(order.getUuid())));
        return orders.stream()
            .map(orderEntity -> OrderDetailsInfo.of(orderEntity, buyerMap.get(orderEntity.getUuid()),
                    orderItemsMap.get(orderEntity.getUuid())))
            .toList();
    }

    public List<OrderDetailsInfo> findOrderAggByStore(UUID storeId, Pageable pageable) {
        List<OrderEntity> orders = orderRepository.findByStore(storeId, pageable);
        Map<UUID, BuyerEntity> buyerMap = orders.stream()
            .collect(Collectors.toMap(BaseEntity::getUuid, order -> buyerRepository.findById(order.getBuyerUuid())
                .orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND_ENTITY))));
        Map<UUID, List<OrderItemEntity>> orderItemsMap = orders.stream()
            .collect(Collectors.toMap(OrderEntity::getUuid,
                    order -> orderItemRepository.findByOrderUuid(order.getUuid())));
        return orders.stream()
            .map(orderEntity -> OrderDetailsInfo.of(orderEntity, buyerMap.get(orderEntity.getUuid()),
                    orderItemsMap.get(orderEntity.getUuid())))
            .toList();
    }

}
