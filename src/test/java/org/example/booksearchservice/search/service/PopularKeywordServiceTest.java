package org.example.booksearchservice.search.service;

import org.example.booksearchservice.search.application.dto.PopularKeywordResponse;
import org.example.booksearchservice.search.application.service.PopularKeywordService;
import org.example.booksearchservice.search.application.usecase.LoadPopularKeywordUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class PopularKeywordServiceTest {

    private LoadPopularKeywordUseCase loadPopularKeywordUseCase;
    private PopularKeywordService popularKeywordService;

    @BeforeEach
    void setUp() {
        loadPopularKeywordUseCase = mock(LoadPopularKeywordUseCase.class);
        popularKeywordService = new PopularKeywordService(loadPopularKeywordUseCase);
    }

    @Test
    @DisplayName("[SUCCESS] 인기 검색어를 조회하면 키워드 목록을 반환한다")

    void testGetPopularKeywords() {
        // given
        List<String> keywords = List.of("java", "spring", "docker");
        when(loadPopularKeywordUseCase.execute()).thenReturn(keywords);

        // when
        PopularKeywordResponse response = popularKeywordService.getPopularKeywords();

        // then
        assertThat(response.keywords()).isEqualTo(keywords);
        verify(loadPopularKeywordUseCase, times(1)).execute();
    }
}
