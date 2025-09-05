package org.example.booksearchservice.search.usecase;

import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.mock.FakeBookInternalAdapter;
import org.example.booksearchservice.search.application.port.BookInternalPort;
import org.example.booksearchservice.search.application.usecase.KeywordSearchUseCase;
import org.example.booksearchservice.search.domain.SearchKeyword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class KeywordSearchUseCaseTest {

    private KeywordSearchUseCase keywordSearchUseCase;

    @BeforeEach
    void setUp() {
        BookInternalPort bookInternalPort = new FakeBookInternalAdapter();
        keywordSearchUseCase = new KeywordSearchUseCase(bookInternalPort);
    }

    @Test
    @DisplayName("[SUCCESS] 키워드로 책을 검색하면 페이징된 책 목록을 반환한다")
    void execute_givenValidKeyword_returnsBookPageResponse() {
        // given
        String keyword = "java";
        Pageable pageable = PageRequest.of(0, 10);

        // when
        BookPageResponse response = keywordSearchUseCase.execute(SearchKeyword.of(keyword), pageable);

        // then
        assertThat(
                response.books().stream()
                        .anyMatch(book ->
                                book.title().toLowerCase().contains(keyword) ||
                                book.subtitle().toLowerCase().contains(keyword))
        ).isTrue();
    }
}
