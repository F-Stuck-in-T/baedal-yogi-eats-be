package com.fstuckint.baedalyogieats.storage.db.core.address;

import com.fstuckint.baedalyogieats.storage.db.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity {

    @Id
    private UUID uuid;

    private String full_address;
    private Long user_id;
}
