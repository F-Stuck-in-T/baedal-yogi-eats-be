package com.fstuckint.baedalyogieats.core.api.store.domain;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRegister categoryRegister;

    private final CategoryReader categoryReader;

    public CategoryService(CategoryRegister categoryRegister, CategoryReader categoryReader) {
        this.categoryRegister = categoryRegister;
        this.categoryReader = categoryReader;
    }

    public CategoryResult register(Category category) {
        return categoryRegister.register(category);
    }

    public List<CategoryResult> read() {
        return categoryReader.read();
    }

}
