package com.fstuckint.baedalyogieats.storage.db.core.address;

import com.fstuckint.baedalyogieats.storage.db.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String full_address;
    private UUID userUuid;

    private boolean isDeleted = false;

    public AddressEntity(String full_address, UUID userUuid) {
        this.full_address = full_address;
        this.userUuid = userUuid;
    }

    public AddressEntity updateAddress(String full_address) {
        this.full_address = full_address;
        return this;
    }

    public AddressEntity deleteAddress() {
        isDeleted = true;
        return this;
    }
}
