package com.fstuckint.baedalyogieats.core.api.address.domain;

import com.fstuckint.baedalyogieats.storage.db.core.address.AddressEntity;

import java.util.UUID;

public record AddressResult(UUID uuid, String full_address) {

    public static AddressResult of(AddressEntity addressEntity) {
        return new AddressResult(
                addressEntity.getUuid(),
                addressEntity.getFull_address());
    }
}
