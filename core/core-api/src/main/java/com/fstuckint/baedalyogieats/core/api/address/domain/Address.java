package com.fstuckint.baedalyogieats.core.api.address.domain;

import com.fstuckint.baedalyogieats.storage.db.core.address.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String full_address;

    private UUID userUuid;

    public AddressEntity toEntity() {
        return new AddressEntity(full_address, userUuid);
    }

}
