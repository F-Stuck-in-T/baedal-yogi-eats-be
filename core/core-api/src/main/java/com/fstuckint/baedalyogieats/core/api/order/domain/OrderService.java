package com.fstuckint.baedalyogieats.core.api.order.domain;

import com.fstuckint.baedalyogieats.core.api.order.domain.dto.*;
import com.fstuckint.baedalyogieats.core.api.order.support.error.*;
import com.fstuckint.baedalyogieats.core.api.order.support.error.CoreApiException;
import com.fstuckint.baedalyogieats.core.api.order.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.payment.domain.*;
import com.fstuckint.baedalyogieats.core.api.payment.support.error.*;
import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import com.fstuckint.baedalyogieats.storage.db.core.payment.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

import static com.fstuckint.baedalyogieats.core.api.payment.support.error.ErrorType.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderStore orderStore;

    private final OrderReader orderReader;

    private final PaymentRepository paymentRepository;

    @Transactional
    public UUID registerOrder(RegisterOrderCommand command) {

        OrderEntity initOrder = command.toEntity();

        BuyerEntity initBuyer = command.getBuyer().toEntity();

        List<OrderItemEntity> initOrderItems = command.getOrderItems()
            .stream()
            .map(orderItemCommand -> orderItemCommand.toEntity(initOrder))
            .toList();

        OrderEntity order = orderStore.storeOrderAgg(initOrder, initBuyer, initOrderItems);

        return order.getUuid();
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
        OrderDetailsInfo orderDetailsInfo = orderReader.getOrderAgg(uuid);
        PaymentEntity payment = paymentRepository.findByOrderUuid(uuid)
            .orElseThrow(() -> new PaymentException(NOT_FOUND_ERROR));
        orderDetailsInfo.addPaymentInfo(PaymentInfo.of(payment));
        return orderDetailsInfo;
    }

    @Transactional(readOnly = true)
    public List<OrderDetailsInfo> retrieveOrderList(Pageable pageable) {
        List<OrderDetailsInfo> orderDetailsInfos = orderReader.findOrderAgg(pageable);
        orderDetailsInfos.stream().forEach(orderDetailsInfo -> {
            PaymentEntity payment = paymentRepository.findByOrderUuid(orderDetailsInfo.getOrderUuid())
                .orElseThrow(() -> new PaymentException(NOT_FOUND_ERROR));
            orderDetailsInfo.addPaymentInfo(PaymentInfo.of(payment));
        });
        return orderDetailsInfos;
    }

    @Transactional(readOnly = true)
    public List<OrderDetailsInfo> retrieveOrderListUser(UUID storeId, Pageable pageable) {
        List<OrderDetailsInfo> orderDetailsInfos = orderReader.findOrderAggByUser(storeId, pageable);
        orderDetailsInfos.stream().forEach(orderDetailsInfo -> {
            PaymentEntity payment = paymentRepository.findByOrderUuid(orderDetailsInfo.getOrderUuid())
                .orElseThrow(() -> new PaymentException(NOT_FOUND_ERROR));
            orderDetailsInfo.addPaymentInfo(PaymentInfo.of(payment));
        });
        return orderDetailsInfos;
    }

    @Transactional(readOnly = true)
    public List<OrderDetailsInfo> retrieveOrderListStore(UUID storeId, Pageable pageable) {
        List<OrderDetailsInfo> orderDetailsInfos = orderReader.findOrderAggByStore(storeId, pageable);
        orderDetailsInfos.stream().forEach(orderDetailsInfo -> {
            PaymentEntity payment = paymentRepository.findByOrderUuid(orderDetailsInfo.getOrderUuid())
                .orElseThrow(() -> new PaymentException(NOT_FOUND_ERROR));
            orderDetailsInfo.addPaymentInfo(PaymentInfo.of(payment));
        });
        return orderDetailsInfos;
    }

}
