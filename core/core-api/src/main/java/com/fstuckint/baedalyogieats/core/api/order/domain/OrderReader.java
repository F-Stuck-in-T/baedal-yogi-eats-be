package com.fstuckint.baedalyogieats.core.api.order.domain;

import com.fstuckint.baedalyogieats.core.api.order.support.error.*;
import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@RequiredArgsConstructor
public class OrderReader {

    private final OrderRepository orderRepository;

    public OrderEntity getByUuid(UUID uuid) {
        return orderRepository.findById(uuid).orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND_ENTITY));
    }

}
