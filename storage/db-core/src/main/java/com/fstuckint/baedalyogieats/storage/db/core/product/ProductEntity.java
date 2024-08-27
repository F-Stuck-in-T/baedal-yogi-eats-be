package com.fstuckint.baedalyogieats.storage.db.core.product;

import com.fstuckint.baedalyogieats.storage.db.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "p_products")
public class ProductEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int unitPrice;

    @Column(nullable = false)
    private UUID storeUuid;

    public ProductEntity() {
    }

    public ProductEntity(String name, String description, int unitPrice, UUID storeUuid) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.storeUuid = storeUuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public UUID getStoreUuid() {
        return storeUuid;
    }

}
