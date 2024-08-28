package com.fstuckint.baedalyogieats.storage.db.core.order;

import com.fstuckint.baedalyogieats.core.enums.order.*;
import com.fstuckint.baedalyogieats.storage.db.core.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "p_orders")
public class OrderEntity extends BaseEntity {

    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private UUID buyerUuid;

    private Integer totalPrice;

    @Builder
    public OrderEntity(Type type, UUID buyerUuid) {
        this.type = type;
        this.status = Status.RECEIVED;
        this.buyerUuid = buyerUuid;
    }

    public void addTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addBuyer(UUID buyerUuid) {
        this.buyerUuid = buyerUuid;
    }

}
