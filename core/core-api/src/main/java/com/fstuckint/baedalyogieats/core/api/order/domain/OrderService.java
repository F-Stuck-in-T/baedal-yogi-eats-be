package com.fstuckint.baedalyogieats.core.api.order.domain;

import com.fstuckint.baedalyogieats.core.api.order.domain.dto.*;
import com.fstuckint.baedalyogieats.core.api.order.support.error.*;
import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderStore orderStore;

    private final OrderReader orderReader;


    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final BuyerRepository buyerRepository;

    @Transactional
    public OrderInfo registerOrder(RegisterOrderCommand command) {

        OrderEntity orderEntity = command.toEntity();

        BuyerEntity buyerEntity = command.getBuyer().toEntity();

        List<OrderItemEntity> orderItemEntities = command.getOrderItems()
            .stream()
            .map(orderItem -> orderItem.toEntity(orderEntity))
            .toList();

        OrderEntity storedOrder = orderStore.storeOrderAgg(orderEntity, orderItemEntities, buyerEntity);
        storedOrder.addTotalPrice(orderItemEntities.stream().mapToInt(OrderItemEntity::getUnitPrice).sum());

        System.out.println("storedOrder = " + storedOrder.getBuyerUuid());

        return new OrderInfo(storedOrder);
    }

    @Transactional
    public void changeOrderReceived(UUID uuid) {
        OrderEntity orderEntity = orderReader.getByUuid(uuid);
        if (!orderEntity.isPending()) {
            throw new CoreApiException(ErrorType.INVALID_STATUS_CHANGE);
        }
        orderEntity.received();
    }

    @Transactional
    public void changeOrderShipping(UUID uuid) {
        OrderEntity orderEntity = orderReader.getByUuid(uuid);
        if (!orderEntity.isReceived()) {
            throw new CoreApiException(ErrorType.INVALID_STATUS_CHANGE);
        }
        orderEntity.shipping();
    }

    @Transactional
    public void changeOrderDelivered(UUID uuid) {
        OrderEntity orderEntity = orderReader.getByUuid(uuid);
        if (!orderEntity.isShipping()) {
            throw new CoreApiException(ErrorType.INVALID_STATUS_CHANGE);
        }
        orderEntity.delivered();
    }

    @Transactional
    public void orderCancel(UUID uuid) {
        OrderEntity orderEntity = orderReader.getByUuid(uuid);
        if (orderEntity.isCancelTimeout(LocalDateTime.now())) {
            throw new CoreApiException(ErrorType.CANCEL_TIME_OUT);
        }
        orderEntity.cancel();
    }

    @Transactional(readOnly = true)
    public OrderDetailsInfo retrieveOrderDetails(UUID uuid) {
        return orderReader.getOrderAgg(uuid);
    }

    @Transactional(readOnly = true)
    public List<OrderDetailsInfo> retrieveOrderList(Pageable pageable) {
        return orderReader.findOrderAgg(pageable);
    }

    public List<OrderDetailsInfo> retrieveOrderListUser(UUID storeId, Pageable pageable) {
        return orderReader.findOrderAggByUser(storeId, pageable);
    }

    public List<OrderDetailsInfo> retrieveOrderListStore(UUID storeId, Pageable pageable) {
        return orderReader.findOrderAggByStore(storeId, pageable);
    }
}
