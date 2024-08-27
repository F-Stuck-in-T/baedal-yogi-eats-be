package com.fstuckint.baedalyogieats.core.api.address.controller.v1.response;

import com.fstuckint.baedalyogieats.storage.db.core.address.Address;
import lombok.Data;

import java.util.UUID;

@Data
public class AddressResponseDto {

    private UUID uuid;

    private String fullAddress;

    public AddressResponseDto(Address address) {
        uuid = address.getUuid();
        fullAddress = address.getFull_address();
    }

}
