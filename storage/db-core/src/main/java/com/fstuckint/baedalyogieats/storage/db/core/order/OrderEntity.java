package com.fstuckint.baedalyogieats.storage.db.core.order;

import com.fstuckint.baedalyogieats.core.enums.order.*;
import com.fstuckint.baedalyogieats.storage.db.core.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "p_orders")
public class OrderEntity extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private UUID buyerUuid;
    @Column(nullable = false)
    private UUID storeUuid;

    private Integer totalPrice;

    private boolean isDeleted;

    @Builder
    public OrderEntity(OrderType orderType, UUID storeUuid) {
        this.orderType = orderType;
        this.orderStatus = OrderStatus.PENDING;
        this.storeUuid = storeUuid;
        this.isDeleted = false;
    }

    public void addTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addBuyer(UUID buyerUuid) {
        this.buyerUuid = buyerUuid;
    }

    public void received() {
        this.orderStatus = OrderStatus.RECEIVED;
    }

    public void shipping() {
        this.orderStatus = OrderStatus.SHIPPING;
    }

    public void delivered() {
        this.orderStatus = OrderStatus.DELIVERED;
    }

    public void cancel() {
        this.isDeleted = true;
    }

    public boolean isPending() {
        return this.orderStatus == OrderStatus.PENDING ? true : false;
    }

    public boolean isReceived() {
        return this.orderStatus == OrderStatus.RECEIVED ? true : false;
    }

    public boolean isShipping() {
        return this.orderStatus == OrderStatus.SHIPPING ? true : false;
    }

    public boolean isCancelTimeout(LocalDateTime cancelTime) {
        return cancelTime.isAfter(this.getCreatedAt().plusMinutes(5));
    }

}
