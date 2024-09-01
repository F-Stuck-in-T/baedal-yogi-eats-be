package com.fstuckint.baedalyogieats.core.api.store.controller.v1;

import com.fstuckint.baedalyogieats.core.api.product.support.response.ApiResponse;
import com.fstuckint.baedalyogieats.core.api.store.controller.v1.request.StoreRegisterRequest;
import com.fstuckint.baedalyogieats.core.api.store.controller.v1.response.StoreResponse;
import com.fstuckint.baedalyogieats.core.api.store.domain.Owner;
import com.fstuckint.baedalyogieats.core.api.store.domain.OwnerStore;
import com.fstuckint.baedalyogieats.core.api.store.domain.Store;
import com.fstuckint.baedalyogieats.core.api.store.domain.StoreResult;
import com.fstuckint.baedalyogieats.core.api.store.domain.StoreService;
import com.fstuckint.baedalyogieats.core.api.user.domain.CurrentUser;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/api/v1/stores")
    public ApiResponse<StoreResponse> registerStore(CurrentUser currentUser,
            @RequestBody StoreRegisterRequest request) {
        Owner owner = new Owner(currentUser.uuid());
        Store store = request.toStore();
        OwnerStore ownerStore = new OwnerStore(owner, store);
        StoreResult result = storeService.register(ownerStore);
        return ApiResponse.success(StoreResponse.of(result));
    }

    @GetMapping("/api/v1/stores/{storeUuid}")
    public ApiResponse<StoreResponse> findStore(@PathVariable String storeUuid) {
        StoreResult result = storeService.find(UUID.fromString(storeUuid));
        return ApiResponse.success(StoreResponse.of(result));
    }

}
