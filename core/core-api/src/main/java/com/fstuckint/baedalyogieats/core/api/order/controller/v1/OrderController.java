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

    @PostMapping // 주문 요청
    public ApiResponse<RegisterOrderResponse> registerOrder(@RequestBody RegisterOrderRequest request) {
        UUID uuid = orderService.registerOrder(request.toCommand());
        return ApiResponse.success(new RegisterOrderResponse(uuid));
    }

    @PutMapping("/{orderUuid}/received") // 주문 접수완료
    public ApiResponse changeReceived(@PathVariable("orderUuid") UUID uuid) {
        orderService.changeOrderReceived(uuid);
        return ApiResponse.success(null);
    }

    @PutMapping("/{orderUuid}/shipping") // 주문 배송중
    public ApiResponse changeShipping(@PathVariable("orderUuid") UUID uuid) {
        orderService.changeOrderShipping(uuid);
        return ApiResponse.success(null);
    }

    @PutMapping("/{orderUuid}/delivered") // 주문 배송완료
    public ApiResponse changeDelivered(@PathVariable("orderUuid") UUID uuid) {
        orderService.changeOrderDelivered(uuid);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{orderUuid}") // 주문 취소
    public ApiResponse orderCancel(@PathVariable("orderUuid") UUID uuid) {
        orderService.orderCancel(uuid);
        return ApiResponse.success(null);
    }

    @GetMapping("/{orderUuid}") // 상세 조회
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

    @GetMapping("/stores/{storeUuid}") // 가게 주문 조회
    public ApiResponse retrieveOrderListOwner(@PathVariable("storeUuid") UUID storeUuid, Pageable pageable) {
        List<OrderDetailsInfo> orderDetailsInfos = orderService.retrieveOrderListStore(storeUuid, pageable);
        return ApiResponse.success(orderDetailsInfos);
    }
}
