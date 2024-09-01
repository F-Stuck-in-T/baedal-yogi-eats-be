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

}
