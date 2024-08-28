package com.fstuckint.baedalyogieats.core.api.order.controller.v1.response;

import com.fstuckint.baedalyogieats.core.api.order.domain.dto.*;
import lombok.*;

import java.util.*;

@Getter
@AllArgsConstructor
public class RegisterOrderResponse {

    private UUID orderUuid;

    public static RegisterOrderResponse of(OrderInfo info) {
        return new RegisterOrderResponse(info.getUuid());
    }

}
