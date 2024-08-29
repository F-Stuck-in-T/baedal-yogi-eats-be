package com.fstuckint.baedalyogieats.core.api.store.controller;

import com.fstuckint.baedalyogieats.core.api.product.support.response.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {

    public StoreController() {
    }

    @PostMapping("/api/v1/stores")
    public ApiResponse<?> registerStore() {
        return ApiResponse.success();
    }

}
