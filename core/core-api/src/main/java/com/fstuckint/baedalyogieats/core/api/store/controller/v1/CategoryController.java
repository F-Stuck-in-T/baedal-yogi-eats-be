package com.fstuckint.baedalyogieats.core.api.store.controller.v1;

import com.fstuckint.baedalyogieats.core.api.store.controller.v1.request.CategoryRegisterRequest;
import com.fstuckint.baedalyogieats.core.api.store.controller.v1.response.CategoryResponse;
import com.fstuckint.baedalyogieats.core.api.store.domain.CategoryResult;
import com.fstuckint.baedalyogieats.core.api.store.domain.CategoryService;
import com.fstuckint.baedalyogieats.core.api.store.support.response.ApiResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/api/v1/category")
    public ApiResponse<CategoryResponse> registerCategory(@RequestBody CategoryRegisterRequest request) {
        CategoryResult categoryResult = categoryService.register(request.toCategory());
        return ApiResponse.success(CategoryResponse.of(categoryResult));
    }

    @GetMapping("/api/v1/category")
    public ApiResponse<List<CategoryResponse>> readCategories() {
        List<CategoryResult> categoryResults = categoryService.read();
        return ApiResponse.success(CategoryResponse.of(categoryResults));
    }

}
