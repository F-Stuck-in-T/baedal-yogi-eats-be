package com.fstuckint.baedalyogieats.storage.db.core.product;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    public static final String CREATED_AT = "createdAt";

    private final JPAQueryFactory jpaQueryFactory;

    public ProductRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ProductEntity> findByCursor(UUID lastUuid, LocalDateTime lastTimestamp, long limit, String sortKey,
            Sort.Direction sort) {
        QProductEntity qProductEntity = QProductEntity.productEntity;

        BooleanExpression cursorCondition = createCursorCondition(qProductEntity, lastUuid, lastTimestamp, sortKey,
                sort);
        OrderSpecifier<?> orderSpecifier = createOrderSpecifier(qProductEntity, sortKey, sort);

        return jpaQueryFactory.selectFrom(qProductEntity)
            .where(cursorCondition)
            .orderBy(orderSpecifier, qProductEntity.uuid.asc())
            .limit(limit)
            .fetch();
    }

    private BooleanExpression createCursorCondition(QProductEntity qProductEntity, UUID lastUuid,
            LocalDateTime lastTimestamp, String sortKey, Sort.Direction sort) {
        if (lastUuid == null || lastTimestamp == null) {
            return qProductEntity.uuid.isNotNull();
        }

        DateTimePath<LocalDateTime> timeField = getTimeFiled(qProductEntity, sortKey);

        if (sort == Sort.Direction.ASC) {
            return timeField.after(lastTimestamp).or(timeField.eq(lastTimestamp).and(qProductEntity.uuid.gt(lastUuid)));
        }
        return timeField.before(lastTimestamp).or(timeField.eq(lastTimestamp).and(qProductEntity.uuid.lt(lastUuid)));
    }

    private OrderSpecifier<?> createOrderSpecifier(QProductEntity qProductEntity, String sortKey, Sort.Direction sort) {

        DateTimePath<LocalDateTime> timeField = getTimeFiled(qProductEntity, sortKey);

        Order order = sort == Sort.Direction.ASC ? Order.ASC : Order.DESC;
        return new OrderSpecifier<>(order, timeField);
    }

    private DateTimePath<LocalDateTime> getTimeFiled(QProductEntity qProductEntity, String sortKey) {
        return CREATED_AT.equals(sortKey) ? qProductEntity.createdAt : qProductEntity.updatedAt;
    }

}
