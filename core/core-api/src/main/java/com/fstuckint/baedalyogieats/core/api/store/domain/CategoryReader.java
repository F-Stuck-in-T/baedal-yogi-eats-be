package com.fstuckint.baedalyogieats.core.api.store.domain;

import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CategoryReader {

    private final CategoryRepository categoryRepository;

    public CategoryReader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResult> read() {
        return categoryRepository.findAll().stream().map(CategoryResult::of).toList();
    }

}
