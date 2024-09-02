package com.fstuckint.baedalyogieats.core.api.store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryEntity;
import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryRepository;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CategoryReaderTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryReader categoryReader;

    private List<CategoryEntity> categories;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryReader = new CategoryReader(categoryRepository);

        categories = IntStream.rangeClosed(1, 15).mapToObj(i -> new CategoryEntity("카테고리" + i)).toList();
    }

    @Test
    void 카테고리_목록을_조회한다() {
        // given
        categoryRepository.saveAll(categories);
        when(categoryRepository.findAll()).thenReturn(categories);

        // when
        List<CategoryResult> results = categoryReader.read();

        // then
        assertThat(results).hasSize(categories.size());
        assertThat(results.getFirst().name()).isEqualTo("카테고리1");
        assertThat(results.getLast().name()).isEqualTo("카테고리15");
    }

}