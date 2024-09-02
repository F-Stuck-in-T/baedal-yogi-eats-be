package com.fstuckint.baedalyogieats.core.api.store.controller.v1.request;

import com.fstuckint.baedalyogieats.core.api.store.domain.Category;

public record CategoryRegisterRequest(String name) {

    public Category toCategory() {
        return new Category(name);
    }
}
