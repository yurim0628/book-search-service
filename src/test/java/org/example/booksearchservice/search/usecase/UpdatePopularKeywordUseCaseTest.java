package org.example.booksearchservice.search.usecase;

import org.example.booksearchservice.search.application.usecase.UpdatePopularKeywordUseCase;
import org.example.booksearchservice.search.domain.SearchKeyword;
import org.example.booksearchservice.search.infrastructure.cache.InMemoryPopularKeywordAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UpdatePopularKeywordUseCaseTest {

    private InMemoryPopularKeywordAdapter inMemoryAdapter;
    private UpdatePopularKeywordUseCase useCase;

    @BeforeEach
    void setUp() {
        inMemoryAdapter = new InMemoryPopularKeywordAdapter();
        useCase = new UpdatePopularKeywordUseCase(inMemoryAdapter);
    }

    @Test
    @DisplayName("[SUCCESS] 인기 검색어 카운트가 증가하는지 확인")
    void execute_incrementsKeywordCount() {
        String keyword = "java";

        useCase.execute(SearchKeyword.of(keyword));

        List<String> topKeywords = inMemoryAdapter.findTop10Keywords();
        assertThat(topKeywords.size()).isEqualTo(1);
    }
}

