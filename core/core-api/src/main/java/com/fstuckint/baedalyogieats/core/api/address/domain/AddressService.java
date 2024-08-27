package com.fstuckint.baedalyogieats.core.api.address.domain;

import com.fstuckint.baedalyogieats.core.api.address.controller.v1.request.AddressRequest;
import com.fstuckint.baedalyogieats.core.api.address.controller.v1.response.AddressResponse;
import com.fstuckint.baedalyogieats.core.api.address.support.error.AddressException;
import com.fstuckint.baedalyogieats.core.api.address.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.common.jwt.UserChecker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {


    private final UserChecker userChecker;
    private final AddressPilot addressPilot;


    private final JwtUtils jwtUtils;

    @Transactional
    public AddressResponse registerAddress(AddressRequest addressRequest, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        userChecker.checkTokenValid(token);
        userChecker.checkTokenNotOwner(token);
        AddressResult addressResult = addressPilot.registerAddress(token, addressRequest);
        return AddressResponse.of(addressResult);
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressListByUser(UUID userUuid, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        userChecker.checkTokenValid(token);
        userChecker.checkTokenNotOwner(token);
        return addressPilot.getAddressListByUser(token, userUuid)
                .stream()
                .map(AddressResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressListByAdmin(HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        userChecker.checkTokenValid(token);
        if (!userChecker.checkAdmin(token)) throw new AddressException(ErrorType.ROLE_ERROR);
        return addressPilot.getAddressListByAdmin()
                .stream()
                .map(AddressResponse::of)
                .toList();
    }

    @Transactional
    public AddressResponse updateAddress(UUID addressId, AddressRequest addressRequest, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        userChecker.checkTokenValid(token);
        if (!userChecker.checkAdmin(token)) {
            userChecker.checkIdentityByUserUuid(token, addressRequest.userUuid());
        }
        return AddressResponse.of(addressPilot.updateAddress(addressId, addressRequest));
    }

    @Transactional
    public void deleteAddress(UUID addressId, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        userChecker.checkTokenValid(token);
        if (!userChecker.checkAdmin(token)) {
            addressPilot.checkIdentity(token, addressId);
        }
        addressPilot.deleteAddress(addressId);
    }

}
