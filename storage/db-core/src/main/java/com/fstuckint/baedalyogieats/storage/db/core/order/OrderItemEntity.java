package com.fstuckint.baedalyogieats.storage.db.core.order;

import com.fstuckint.baedalyogieats.storage.db.core.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "p_order_items")
public class OrderItemEntity extends BaseEntity {

    private String name;

    private Integer unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity orderEntity;

    @Column(nullable = false)
    private UUID productUuid;

    @Builder
    public OrderItemEntity(String name, Integer unitPrice, OrderEntity orderEntity, UUID productUuid) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.orderEntity = orderEntity;
        this.productUuid = productUuid;
    }

}
