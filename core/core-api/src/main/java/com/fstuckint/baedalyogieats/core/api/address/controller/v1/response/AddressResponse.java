package com.fstuckint.baedalyogieats.core.api.address.controller.v1.response;

import com.fstuckint.baedalyogieats.core.api.address.domain.AddressResult;

import java.util.UUID;

public record AddressResponse(UUID uuid, String address) {

    public static AddressResponse of(AddressResult addressResult) {
        return new AddressResponse(addressResult.uuid(), addressResult.full_address());
    }
}
