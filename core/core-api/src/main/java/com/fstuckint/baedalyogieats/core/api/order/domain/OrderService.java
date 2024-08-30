package com.fstuckint.baedalyogieats.core.api.order.domain;

import com.fstuckint.baedalyogieats.core.api.order.domain.dto.*;
import com.fstuckint.baedalyogieats.core.api.order.support.error.*;
import com.fstuckint.baedalyogieats.core.enums.order.*;
import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderStore orderStore;

    private final OrderReader orderReader;

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
    public void orderCancel(UUID uuid) throws CoreApiException {
        OrderEntity orderEntity = orderReader.getByUuid(uuid);
        if (orderEntity.isCancelTimeout(LocalDateTime.now())) {
            throw new CoreApiException(ErrorType.CANCEL_TIME_OUT);
        }
        orderEntity.cancel();
    }

}
