package com.fstuckint.baedalyogieats.core.api.address.controller.v1;

import com.fstuckint.baedalyogieats.core.api.address.controller.v1.request.AddressRequest;
import com.fstuckint.baedalyogieats.core.api.address.controller.v1.response.AddressResponse;
import com.fstuckint.baedalyogieats.core.api.address.domain.AddressService;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> registerAddress(@RequestBody AddressRequest addressDto,
            HttpServletRequest request) {
        AddressResponse data = addressService.registerAddress(addressDto, request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/users/{userUuid}")
    public ResponseEntity<ApiResponse<?>> getAddressListByUser(@PathVariable UUID userUuid,
            HttpServletRequest request) {
        List<AddressResponse> data = addressService.getAddressListByUser(userUuid, request);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<?>> getAddressListByAdmin(HttpServletRequest request) {
        List<AddressResponse> data = addressService.getAddressListByAdmin(request);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse<?>> updateAddress(@PathVariable UUID addressId,
            @RequestBody AddressRequest addressRequest, HttpServletRequest request) {
        AddressResponse data = addressService.updateAddress(addressId, addressRequest, request);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<?>> deleteAddress(@PathVariable UUID addressId, HttpServletRequest request) {
        addressService.deleteAddress(addressId, request);
        return ResponseEntity.ok(ApiResponse.success());
    }

}
