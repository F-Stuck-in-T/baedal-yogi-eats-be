package com.fstuckint.baedalyogieats.storage.db.core.order;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID>, OrderRepositoryCustom {

}
