package com.fstuckint.baedalyogieats.core.api.product.controller.v1;

import com.fstuckint.baedalyogieats.core.api.product.controller.v1.request.ProductRegisterRequest;
import com.fstuckint.baedalyogieats.core.api.product.controller.v1.response.ProductResponse;
import com.fstuckint.baedalyogieats.core.api.product.domain.ProductResult;
import com.fstuckint.baedalyogieats.core.api.product.domain.ProductService;
import com.fstuckint.baedalyogieats.core.api.product.support.response.ApiResponse;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // TODO: 다중 상품 등록 기능
    // TODO: 가게 UUID 유효성 검사
    @PostMapping("/api/v1/products")
    public ApiResponse<ProductResponse> registerProduct(@RequestBody ProductRegisterRequest request) {
        ProductResult result = productService.register(request.toProduct());
        return ApiResponse.success(ProductResponse.of(result));
    }

    @GetMapping("/api/v1/products/{productUuid}")
    public ApiResponse<ProductResponse> findProduct(@PathVariable String productUuid) {
        ProductResult result = productService.find(UUID.fromString(productUuid));
        return ApiResponse.success(ProductResponse.of(result));
    }

}
