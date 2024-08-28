package com.fstuckint.baedalyogieats.core.api.address.controller.v1.request;

import com.fstuckint.baedalyogieats.core.api.address.domain.Address;

import java.util.UUID;

public record AddressRequest(String address, UUID userUuid) {

    public Address toAddress() {
        return new Address(address, userUuid);
    }
}
