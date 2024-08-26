package com.fstuckint.baedalyogieats.core.api.address.controller.v1;

import com.fstuckint.baedalyogieats.core.api.address.controller.v1.request.AddressDto;
import com.fstuckint.baedalyogieats.core.api.address.domain.AddressService;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> registerAddress(@RequestBody AddressDto addressDto, HttpServletRequest request) {
        return addressService.registerAddress(addressDto, request);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>> getAddressList(HttpServletRequest request) {
        return addressService.getAddressList(request);
    }

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<?>> getAdminList(HttpServletRequest request) {
        return addressService.getAdminList(request);
    }
    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse<?>> updateAddress(@PathVariable UUID addressId, @RequestBody AddressDto addressDto, HttpServletRequest request) {
        return addressService.updateAddress(addressId, addressDto, request);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<?>> deleteAddress(@PathVariable UUID addressId, HttpServletRequest request) {
        return addressService.deleteAddress(addressId, request);
    }


}
