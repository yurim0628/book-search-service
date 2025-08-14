package org.example.booksearchservice.search.usecase;

import org.example.booksearchservice.search.application.usecase.LoadPopularKeywordUseCase;
import org.example.booksearchservice.search.infrastructure.cache.InMemoryPopularKeywordAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LoadPopularKeywordUseCaseTest {

    private LoadPopularKeywordUseCase loadPopularKeywordUseCase;

    @BeforeEach
    void setUp() {
        InMemoryPopularKeywordAdapter popularKeywordAdapter = new InMemoryPopularKeywordAdapter();
        loadPopularKeywordUseCase = new LoadPopularKeywordUseCase(popularKeywordAdapter);
        for (int i = 1; i <= 15; i++) {
            String keyword = "keyword" + i;
            for (int j = 0; j < i; j++) {
                popularKeywordAdapter.incrementCount(keyword);
            }
        }
    }

    @Test
    @DisplayName("[SUCCESS] 인기 검색어 상위 10개를 반환한다")
    void testFindTop10Keywords() {
        List<String> topKeywords = loadPopularKeywordUseCase.execute();

        assertThat(topKeywords).hasSize(10);
        assertThat(topKeywords.get(0)).isEqualTo("keyword15");
        assertThat(topKeywords).contains("keyword10");
    }
}
