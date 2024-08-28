package com.fstuckint.baedalyogieats.core.api.address.controller.v1;

import com.fstuckint.baedalyogieats.core.api.address.controller.v1.request.AddressRequest;
import com.fstuckint.baedalyogieats.core.api.address.controller.v1.response.AddressResponse;
import com.fstuckint.baedalyogieats.core.api.address.domain.AddressService;
import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
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
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        AddressResponse data = addressService.registerAddress(addressDto, bearerToken);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping("/users/{userUuid}")
    public ResponseEntity<ApiResponse<?>> getAddressListByUser(@PathVariable UUID userUuid,
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        List<AddressResponse> data = addressService.getAddressListByUser(userUuid, bearerToken);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<?>> getAddressListByAdmin() {
        List<AddressResponse> data = addressService.getAddressListByAdmin();
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse<?>> updateAddress(@PathVariable UUID addressId,
            @RequestBody AddressRequest addressRequest,
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        AddressResponse data = addressService.updateAddress(addressId, addressRequest, bearerToken);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<?>> deleteAddress(@PathVariable UUID addressId,
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        AddressResponse data = addressService.deleteAddress(addressId, bearerToken);
        return ResponseEntity.ok(ApiResponse.success());
    }

}
