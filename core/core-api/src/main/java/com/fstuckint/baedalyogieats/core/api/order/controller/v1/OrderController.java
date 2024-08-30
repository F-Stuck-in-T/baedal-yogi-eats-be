package com.fstuckint.baedalyogieats.core.api.order.controller.v1;

import com.fstuckint.baedalyogieats.core.api.order.controller.v1.request.*;
import com.fstuckint.baedalyogieats.core.api.order.controller.v1.response.*;
import com.fstuckint.baedalyogieats.core.api.order.domain.*;
import com.fstuckint.baedalyogieats.core.api.order.domain.dto.*;
import com.fstuckint.baedalyogieats.core.api.order.support.response.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<RegisterOrderResponse> registerOrder(@RequestBody RegisterOrderRequest request) {
        OrderInfo info = orderService.registerOrder(request.toCommand());
        return ApiResponse.success(RegisterOrderResponse.of(info));
    }

    @PutMapping("/{orderUuid}/received")
    public ApiResponse changeReceived(@PathVariable("orderUuid") UUID uuid) {
        orderService.changeOrderReceived(uuid);
        return ApiResponse.success(null);
    }

    @PutMapping("/{orderUuid}/shipping")
    public ApiResponse changeShipping(@PathVariable("orderUuid") UUID uuid) {
        orderService.changeOrderShipping(uuid);
        return ApiResponse.success(null);
    }

    @PutMapping("/{orderUuid}/delivered")
    public ApiResponse changeDelivered(@PathVariable("orderUuid") UUID uuid) {
        orderService.changeOrderDelivered(uuid);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{orderUuid}")
    public ApiResponse orderCancel(@PathVariable("orderUuid") UUID uuid) {
        orderService.orderCancel(uuid);
        return ApiResponse.success(null);
    }

}
