package com.fstuckint.baedalyogieats.storage.db.core.order;

import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface BuyerRepository extends JpaRepository<BuyerEntity, UUID> {

    default BuyerEntity add(BuyerEntity buyerEntity) {
        return save(buyerEntity);
    }

}
