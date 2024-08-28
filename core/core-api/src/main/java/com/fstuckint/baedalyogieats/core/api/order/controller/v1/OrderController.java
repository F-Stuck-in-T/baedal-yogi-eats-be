package com.fstuckint.baedalyogieats.core.api.order.controller.v1;

import com.fstuckint.baedalyogieats.core.api.order.controller.v1.request.*;
import com.fstuckint.baedalyogieats.core.api.order.controller.v1.response.*;
import com.fstuckint.baedalyogieats.core.api.order.domain.*;
import com.fstuckint.baedalyogieats.core.api.order.domain.dto.*;
import com.fstuckint.baedalyogieats.core.api.order.support.response.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<RegisterOrderResponse> registerOrder(@RequestBody RegisterOrderRequest request) {
        OrderInfo info = orderService.register(request.toCommand());
        return ApiResponse.success(RegisterOrderResponse.of(info));
    }

}
