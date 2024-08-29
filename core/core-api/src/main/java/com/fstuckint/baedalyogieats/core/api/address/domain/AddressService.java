package com.fstuckint.baedalyogieats.core.api.address.domain;

import com.fstuckint.baedalyogieats.core.api.address.controller.v1.request.AddressRequest;
import com.fstuckint.baedalyogieats.core.api.address.controller.v1.response.AddressResponse;
import com.fstuckint.baedalyogieats.core.api.address.support.error.AddressException;
import com.fstuckint.baedalyogieats.core.api.address.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
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

    private final AddressPilot addressPilot;

    private final JwtUtils jwtUtils;

    @Transactional
    public AddressResponse registerAddress(AddressRequest addressRequest, String bearerToken) {
        if (jwtUtils.checkAdmin(bearerToken))
            return AddressResponse.of(addressPilot.registerAddressAdmin(addressRequest));
        if (jwtUtils.checkCustomer(bearerToken))
            return AddressResponse.of(addressPilot.registerAddress(bearerToken, addressRequest));
        throw new AddressException(ErrorType.DEFAULT_ERROR);
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressListByUser(UUID userUuid, String bearerToken) {
        if (jwtUtils.checkAdmin(bearerToken))
            return addressPilot.getAddressListByUserAdmin(userUuid).stream().map(AddressResponse::of).toList();
        if (jwtUtils.checkCustomer(bearerToken))
            return addressPilot.getAddressListByUser(userUuid, bearerToken).stream().map(AddressResponse::of).toList();
        throw new AddressException(ErrorType.DEFAULT_ERROR);
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressListByAdmin() {
        return addressPilot.getAllAddressAdmin().stream().map(AddressResponse::of).toList();
    }

    @Transactional
    public AddressResponse updateAddress(UUID addressUuid, AddressRequest addressRequest, String bearerToken) {
        if (jwtUtils.checkAdmin(bearerToken))
            return AddressResponse.of(addressPilot.updateAddressAdmin(addressUuid, addressRequest));
        if (jwtUtils.checkCustomer(bearerToken))
            return AddressResponse.of(addressPilot.updateAddress(addressUuid, addressRequest, bearerToken));
        throw new AddressException(ErrorType.DEFAULT_ERROR);
    }

    @Transactional
    public AddressResponse deleteAddress(UUID addressUuid, String bearerToken) {
        if (jwtUtils.checkAdmin(bearerToken))
            return AddressResponse.of(addressPilot.deleteAddressAdmin(addressUuid));
        if (jwtUtils.checkCustomer(bearerToken))
            return AddressResponse.of(addressPilot.deleteAddress(addressUuid, bearerToken));
        throw new AddressException(ErrorType.DEFAULT_ERROR);
    }

}
