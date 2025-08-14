package org.example.booksearchservice.search.service;

import org.assertj.core.api.AssertionsForClassTypes;
import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.search.application.dto.SearchResponse;
import org.example.booksearchservice.search.application.service.SearchQueryParser;
import org.example.booksearchservice.search.application.service.SearchService;
import org.example.booksearchservice.search.application.usecase.KeywordSearchUseCase;
import org.example.booksearchservice.search.application.usecase.OperatorSearchUseCase;
import org.example.booksearchservice.search.domain.SearchOperator;
import org.example.booksearchservice.search.domain.SearchQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.booksearchservice.fixture.BookFixture.createBook;
import static org.example.booksearchservice.search.domain.SearchOperator.NONE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchServiceTest {

    private KeywordSearchUseCase keywordSearchUseCase;
    private Map<String, OperatorSearchUseCase> operatorSearchUseCaseMap;
    private SearchQueryParser searchQueryParser;
    private SearchService searchService;

    @BeforeEach
    void setUp() {
        keywordSearchUseCase = mock(KeywordSearchUseCase.class);
        operatorSearchUseCaseMap = Map.of(
                NONE.name(), mock(OperatorSearchUseCase.class)
        );
        searchQueryParser = mock(SearchQueryParser.class);
        searchService = new SearchService(keywordSearchUseCase, operatorSearchUseCaseMap, searchQueryParser);
    }

    @Test
    @DisplayName("[SUCCESS] 키워드로 책을 검색하면 메타데이터가 포함된 검색 결과를 반환한다")
    void searchBooksByKeyword_returnsSearchResponse() {
        // given
        String keyword = "java";
        Pageable pageable = PageRequest.of(0, 10);

        List<Book> books = List.of(createBook());
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        BookPageResponse bookPageResponse = BookPageResponse.of(bookPage);

        when(keywordSearchUseCase.execute(keyword, pageable)).thenReturn(bookPageResponse);

        // when
        SearchResponse response = searchService.searchBooksByKeyword(keyword, pageable);

        // then
        AssertionsForClassTypes.assertThat(
                response.books().stream()
                        .anyMatch(book ->
                                book.title().toLowerCase().contains(keyword) ||
                                        book.subtitle().toLowerCase().contains(keyword))
        ).isTrue();
        assertThat(response.searchMetaData().strategy()).isEqualTo(NONE);
        assertThat(response.searchQuery()).isEqualTo(keyword);
    }

    @Test
    @DisplayName("[SUCCESS] 복합 쿼리로 책을 검색하면 연산자 전략과 함께 검색 결과를 반환한다")
    void searchBooksByComplexQuery_returnsSearchResponseWithOperator() {
        // given
        String query = "java|spring";
        Pageable pageable = PageRequest.of(0, 10);

        SearchOperator operator = SearchOperator.OR;
        SearchQuery searchQuery = SearchQuery.builder()
                .firstKeyword("java")
                .secondKeyword("spring")
                .operator(operator)
                .build();

        List<Book> books = List.of(createBook());
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        BookPageResponse bookPageResponse = BookPageResponse.of(bookPage);

        OperatorSearchUseCase operatorSearchUseCase = mock(OperatorSearchUseCase.class);
        operatorSearchUseCaseMap = Map.of(operator.name(), operatorSearchUseCase);

        searchService = new SearchService(keywordSearchUseCase, operatorSearchUseCaseMap, searchQueryParser);

        when(searchQueryParser.parse(query)).thenReturn(searchQuery);
        when(operatorSearchUseCase.execute("java", "spring", pageable)).thenReturn(bookPageResponse);

        // when
        SearchResponse response = searchService.searchBooksByComplexQuery(query, pageable);

        // then
        assertThat(response.books()).hasSize(1);
        assertThat(response.searchQuery()).isEqualTo(query);
        assertThat(response.searchMetaData().strategy()).isEqualTo(operator);
    }
}
