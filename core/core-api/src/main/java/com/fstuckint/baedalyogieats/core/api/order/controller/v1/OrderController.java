package com.fstuckint.baedalyogieats.core.api.order.controller.v1;

import com.fstuckint.baedalyogieats.core.api.order.controller.v1.request.*;
import com.fstuckint.baedalyogieats.core.api.order.controller.v1.response.*;
import com.fstuckint.baedalyogieats.core.api.order.domain.*;
import com.fstuckint.baedalyogieats.core.api.order.domain.dto.*;
import com.fstuckint.baedalyogieats.core.api.order.support.response.*;
import lombok.*;
import org.springframework.data.domain.*;
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

    @GetMapping("/{orderUuid}")
    public ApiResponse<OrderDetailsInfo> retrieveOrderDetails(@PathVariable("orderUuid") UUID uuid) {
        OrderDetailsInfo orderDetailsInfo = orderService.retrieveOrderDetails(uuid);
        return ApiResponse.success(orderDetailsInfo);
    }

    @GetMapping // 관리자 주문 조회
    public ApiResponse<List<OrderDetailsInfo>> retrieveOrderList(Pageable pageable) {
        List<OrderDetailsInfo> orderDetailsInfos = orderService.retrieveOrderList(pageable);
        return ApiResponse.success(orderDetailsInfos);
    }

    @GetMapping("/users/{userId}") // 유저 주문 조회
    public ApiResponse retrieveOrderListUser(@PathVariable("userId") UUID userId, Pageable pageable) {
        List<OrderDetailsInfo> orderDetailsInfos = orderService.retrieveOrderListUser(userId, pageable);
        return ApiResponse.success(orderDetailsInfos);
    }

    @GetMapping("/stores/{storeId}") // 가게 주문 조회
    public ApiResponse retrieveOrderListOwner(@PathVariable("storedId") UUID storeId, Pageable pageable) {
        orderService.retrieveOrderListStore(storeId, pageable);
        return ApiResponse.success();
    }
}
