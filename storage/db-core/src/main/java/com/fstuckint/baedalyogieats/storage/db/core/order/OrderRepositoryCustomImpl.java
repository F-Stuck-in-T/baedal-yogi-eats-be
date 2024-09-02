package com.fstuckint.baedalyogieats.storage.db.core.order;

import com.querydsl.core.*;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.*;
import jakarta.persistence.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.fstuckint.baedalyogieats.storage.db.core.order.QBuyerEntity.*;
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

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(buyerEntity.userUuid.eq(userId)).and(orderEntity.isDeleted.eq(false));

        JPAQuery<OrderEntity> query = jpaQueryFactory.select(orderEntity)
            .from(orderEntity)
            .leftJoin(buyerEntity)
            .on(orderEntity.buyerUuid.eq(buyerEntity.uuid))
            .where(booleanBuilder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        pageable.getSort().forEach(order -> {
            PathBuilder<OrderEntity> orderPath = new PathBuilder<>(OrderEntity.class, "orderEntity");
            OrderSpecifier<?> orderSpecifier = new OrderSpecifier(order.isAscending() ? Order.ASC : Order.DESC,
                    orderPath.get(order.getProperty()));
            query.orderBy(orderSpecifier);
        });

        return query.fetch();
    }

    @Override
    public List<OrderEntity> findByStore(UUID storeUuid, Pageable pageable) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(orderEntity.storeUuid.eq(storeUuid)).and(orderEntity.isDeleted.eq(false));

        JPAQuery<OrderEntity> query = jpaQueryFactory.selectFrom(orderEntity)
            .where(booleanBuilder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        pageable.getSort().forEach(order -> {
            PathBuilder<OrderEntity> orderPath = new PathBuilder<>(OrderEntity.class, "orderEntity");
            OrderSpecifier<?> orderSpecifier = new OrderSpecifier(order.isAscending() ? Order.ASC : Order.DESC,
                    orderPath.get(order.getProperty()));
            query.orderBy(orderSpecifier);
        });

        return query.fetch();
    }

}
