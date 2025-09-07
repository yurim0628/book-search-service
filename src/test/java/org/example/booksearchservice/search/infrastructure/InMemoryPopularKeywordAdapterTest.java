package org.example.booksearchservice.search.infrastructure;

import org.example.booksearchservice.search.domain.SearchKeyword;
import org.example.booksearchservice.search.infrastructure.cache.InMemoryPopularKeywordAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryPopularKeywordAdapterTest {

    private InMemoryPopularKeywordAdapter inMemoryPopularKeywordAdapter;

    @BeforeEach
    void setUp() {
        inMemoryPopularKeywordAdapter = new InMemoryPopularKeywordAdapter();
    }

    @Test
    @DisplayName("키워드 카운트가 정상적으로 증가하는지 테스트")
    void incrementCount_increasesCountCorrectly() {
        inMemoryPopularKeywordAdapter.incrementCount(SearchKeyword.of("java"));
        inMemoryPopularKeywordAdapter.incrementCount(SearchKeyword.of("java"));

        List<String> topKeywords = inMemoryPopularKeywordAdapter.findTop10Keywords();

        assertEquals(1, topKeywords.size(), "인기 검색어 목록 크기 확인");
        assertEquals("java", topKeywords.get(0), "최상위 키워드가 'java'인지 확인");
    }

    @Test
    @DisplayName("최상위 10개의 키워드가 올바르게 조회되는지 테스트")
    void findTop10Keywords_returnsTop10() {
        for (int i = 0; i < 20; i++) {
            String keyword = "keyword" + i;
            for (int j = 0; j < i; j++) {
                inMemoryPopularKeywordAdapter.incrementCount(SearchKeyword.of(keyword));
            }
        }

        List<String> topKeywords = inMemoryPopularKeywordAdapter.findTop10Keywords();

        assertEquals(10, topKeywords.size(), "최상위 10개 키워드 개수 확인");
        assertTrue(topKeywords.contains("keyword19"), "최상위 키워드에 keyword19 포함 확인");
        assertTrue(topKeywords.contains("keyword10"), "최상위 키워드에 keyword10 포함 확인");
        assertFalse(topKeywords.contains("keyword0"), "최상위 키워드에 keyword0 미포함 확인");
    }
}
