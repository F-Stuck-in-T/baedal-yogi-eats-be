package com.fstuckint.baedalyogieats.storage.db.core.category;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    default CategoryEntity add(CategoryEntity categoryEntity) {
        return save(categoryEntity);
    }

    Optional<CategoryEntity> findByUuid(UUID uuid);

}
