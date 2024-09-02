package com.fstuckint.baedalyogieats.core.api.store.domain;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRegister categoryRegister;

    public CategoryService(CategoryRegister categoryRegister) {
        this.categoryRegister = categoryRegister;
    }

    public CategoryResult register(Category category) {
        return categoryRegister.register(category);
    }

}
