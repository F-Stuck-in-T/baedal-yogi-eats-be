package com.fstuckint.baedalyogieats.storage.db.core.category;

import static org.assertj.core.api.Assertions.assertThat;

import com.fstuckint.baedalyogieats.storage.db.CoreDbContextTest;
import org.junit.jupiter.api.Test;

class CategoryRepositoryIT extends CoreDbContextTest {

    private final CategoryRepository categoryRepository;

    public CategoryRepositoryIT(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Test
    void 카테고리가_추가된_후_카테고리_UUID로_조회돼야_한다() {
        // given
        CategoryEntity category = categoryRepository.add(new CategoryEntity("한식"));

        // when
        CategoryEntity addedCategory = categoryRepository.findByUuid(category.getUuid())
            .orElseThrow(() -> new AssertionError("Category not found"));

        // then
        assertThat(addedCategory).isNotNull();
        assertThat(addedCategory.getUuid()).isEqualTo(category.getUuid());
        assertThat(addedCategory.getName()).isEqualTo(category.getName());
    }

}