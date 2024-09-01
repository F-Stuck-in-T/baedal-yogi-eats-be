package com.fstuckint.baedalyogieats.storage.db.core.store;

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

public class StoreRepositoryImpl implements StoreRepositoryCustom {

    public static final String CREATED_AT = "createdAt";

    private final JPAQueryFactory jpaQueryFactory;

    public StoreRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<StoreEntity> findByCursor(UUID lastUuid, LocalDateTime lastTimestamp, long limit, String sortKey,
            Sort.Direction sort) {
        QStoreEntity qStoreEntity = QStoreEntity.storeEntity;

        BooleanExpression cursorCondition = createCursorCondition(qStoreEntity, lastUuid, lastTimestamp, sortKey, sort);
        OrderSpecifier<?> orderSpecifier = createOrderSpecifier(qStoreEntity, sortKey, sort);

        return jpaQueryFactory.selectFrom(qStoreEntity)
            .where(cursorCondition)
            .orderBy(orderSpecifier, qStoreEntity.uuid.asc())
            .limit(limit)
            .fetch();
    }

    private BooleanExpression createCursorCondition(QStoreEntity qStoreEntity, UUID lastUuid,
            LocalDateTime lastTimestamp, String sortKey, Sort.Direction sort) {
        if (lastUuid == null || lastTimestamp == null) {
            return qStoreEntity.uuid.isNotNull();
        }

        DateTimePath<LocalDateTime> timeField = getTimeFiled(qStoreEntity, sortKey);

        if (sort == Sort.Direction.ASC) {
            return timeField.after(lastTimestamp).or(timeField.eq(lastTimestamp).and(qStoreEntity.uuid.gt(lastUuid)));
        }
        return timeField.before(lastTimestamp).or(timeField.eq(lastTimestamp).and(qStoreEntity.uuid.lt(lastUuid)));
    }

    private OrderSpecifier<?> createOrderSpecifier(QStoreEntity qStoreEntity, String sortKey, Sort.Direction sort) {

        DateTimePath<LocalDateTime> timeField = getTimeFiled(qStoreEntity, sortKey);

        Order order = sort == Sort.Direction.ASC ? Order.ASC : Order.DESC;
        return new OrderSpecifier<>(order, timeField);
    }

    private DateTimePath<LocalDateTime> getTimeFiled(QStoreEntity qStoreEntity, String sortKey) {
        return CREATED_AT.equals(sortKey) ? qStoreEntity.createdAt : qStoreEntity.updatedAt;
    }

}
