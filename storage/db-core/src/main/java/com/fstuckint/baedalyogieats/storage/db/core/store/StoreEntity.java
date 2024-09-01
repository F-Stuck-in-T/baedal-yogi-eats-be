package com.fstuckint.baedalyogieats.storage.db.core.store;

import com.fstuckint.baedalyogieats.storage.db.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "p_stores")
public class StoreEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String fullAddress;

    @Column(nullable = false)
    private UUID userUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity categoryEntity;

    public StoreEntity() {
    }

    public StoreEntity(String name, String description, String fullAddress, UUID userUuid,
            CategoryEntity categoryEntity) {
        this.name = name;
        this.description = description;
        this.fullAddress = fullAddress;
        this.userUuid = userUuid;
        this.categoryEntity = categoryEntity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

}
