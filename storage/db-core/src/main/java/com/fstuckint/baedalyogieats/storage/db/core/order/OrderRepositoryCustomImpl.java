package com.fstuckint.baedalyogieats.storage.db.core.order;

import com.querydsl.jpa.impl.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.fstuckint.baedalyogieats.storage.db.core.order.QOrderEntity.*;

@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final EntityManager entityManager;

    private JPAQueryFactory jpaQueryFactory;

    public OrderRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<OrderEntity> findByBuyer(UUID userId, Pageable pageable) {
        return jpaQueryFactory.selectFrom(orderEntity)
                .where(orderEntity.buyerUuid.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
