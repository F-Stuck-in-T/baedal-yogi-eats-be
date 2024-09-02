package com.fstuckint.baedalyogieats.core.api.store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryEntity;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CategoryServiceTest {

    private UUID categoryUuid;

    @Mock
    private CategoryRegister categoryRegister;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        this.categoryUuid = UUID.randomUUID();

        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryService(categoryRegister);
    }

    @Test
    void 카테고리를_등록한다() {
        // given
        String name = "한식";
        Category category = new Category(name);
        CategoryEntity categoryEntity = category.toEntity();
        ReflectionTestUtils.setField(categoryEntity, "uuid", categoryUuid);
        when(categoryRegister.register(category)).thenReturn(CategoryResult.of(categoryEntity));

        // when
        CategoryResult result = categoryService.register(category);

        // then
        assertThat(result).isNotNull();
        assertThat(result.uuid()).isEqualTo(categoryUuid);
        assertThat(result.name()).isEqualTo(name);
    }

}