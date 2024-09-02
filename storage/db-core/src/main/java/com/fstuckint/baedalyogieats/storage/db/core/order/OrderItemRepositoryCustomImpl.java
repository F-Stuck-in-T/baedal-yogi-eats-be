package com.fstuckint.baedalyogieats.storage.db.core.order;

import com.querydsl.jpa.impl.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.fstuckint.baedalyogieats.storage.db.core.order.QOrderItemEntity.*;

@Repository
public class OrderItemRepositoryCustomImpl implements OrderItemRepositoryCustom {

    private final EntityManager em;

    private JPAQueryFactory jpaQueryFactory;

    public OrderItemRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderItemEntity> findByOrderUuid(UUID orderUuid) {
        return jpaQueryFactory.selectFrom(orderItemEntity)
            .where(orderItemEntity.orderEntity.uuid.eq(orderUuid))
            .fetch();
    }

}
