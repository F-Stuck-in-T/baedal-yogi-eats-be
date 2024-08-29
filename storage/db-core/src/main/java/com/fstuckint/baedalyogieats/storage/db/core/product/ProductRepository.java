package com.fstuckint.baedalyogieats.storage.db.core.product;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID>, ProductRepositoryCustom {

    default ProductEntity add(ProductEntity productEntity) {
        return save(productEntity);
    }

    Optional<ProductEntity> findByUuid(UUID uuid);

}
