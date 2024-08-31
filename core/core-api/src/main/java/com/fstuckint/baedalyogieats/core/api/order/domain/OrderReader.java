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
        OrderEntity orderEntity = orderRepository.findById(uuid).orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND_ENTITY));
        BuyerEntity buyerEntity = buyerRepository.findById(orderEntity.getBuyerUuid()).orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND_ENTITY));
        List<OrderItemEntity> orderItemEntities = orderItemRepository.findByOrderUuid(orderEntity.getUuid());
        return OrderDetailsInfo.of(orderEntity, buyerEntity, orderItemEntities);
    }

    public List<OrderDetailsInfo> findOrderAgg(Pageable pageable) {
        Page<OrderEntity> orderEntities = orderRepository.findAll(pageable);
        List<OrderEntity> orders = orderEntities.getContent();
        Map<UUID, BuyerEntity> buyerMap = orders.stream().collect(Collectors.toMap(BaseEntity::getUuid, order -> buyerRepository.findById(order.getBuyerUuid()).orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND_ENTITY))));
        Map<UUID, List<OrderItemEntity>> orderItemsMap = orders.stream().collect(Collectors.toMap(OrderEntity::getUuid, orderEntity -> orderItemRepository.findByOrderUuid(orderEntity.getUuid())));
        return orderEntities.stream().map(orderEntity -> OrderDetailsInfo.of(orderEntity, buyerMap.get(orderEntity.getUuid()), orderItemsMap.get(orderEntity.getUuid()))).toList();
    }

    public List<OrderDetailsInfo> findOrderAggByUser(UUID userId, Pageable pageable) {
        List<OrderEntity> orders = orderRepository.findByBuyer(userId, pageable);
        Map<UUID, BuyerEntity> buyerMap = orders.stream().collect(Collectors.toMap(BaseEntity::getUuid, order -> buyerRepository.findById(order.getBuyerUuid()).orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND_ENTITY))));
        Map<UUID, List<OrderItemEntity>> orderItemsMap = orders.stream().collect(Collectors.toMap(OrderEntity::getUuid, orderEntity -> orderItemRepository.findByOrderUuid(orderEntity.getUuid())));
        return orders.stream().map(orderEntity -> OrderDetailsInfo.of(orderEntity, buyerMap.get(orderEntity.getUuid()), orderItemsMap.get(orderEntity.getUuid()))).toList();
    }

    public List<OrderDetailsInfo> findOrderAggByStore(UUID storeId, Pageable pageable) {
        return null;
    }
}
