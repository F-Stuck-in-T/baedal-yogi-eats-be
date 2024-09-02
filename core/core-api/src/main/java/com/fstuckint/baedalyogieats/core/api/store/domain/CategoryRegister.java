package com.fstuckint.baedalyogieats.core.api.store.domain;

import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class CategoryRegister {

    private final CategoryRepository categoryRepository;

    public CategoryRegister(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResult register(Category category) {
        return CategoryResult.of(categoryRepository.add(category.toEntity()));
    }

}
