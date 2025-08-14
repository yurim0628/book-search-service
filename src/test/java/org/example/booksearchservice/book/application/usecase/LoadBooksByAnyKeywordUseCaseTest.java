package org.example.booksearchservice.book.application.usecase;

import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.mock.FakeLoadBookAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

class LoadBooksByAnyKeywordUseCaseTest {

    private LoadBooksByAnyKeywordUseCase loadBooksByAnyKeywordUseCase;

    @BeforeEach
    void setUp() {
        FakeLoadBookAdapter fakeLoadBookAdapter = new FakeLoadBookAdapter();
        loadBooksByAnyKeywordUseCase = new LoadBooksByAnyKeywordUseCase(fakeLoadBookAdapter);
    }

    @Test
    @DisplayName("[SUCCESS] 두 키워드 중 하나라도 포함하는 도서 목록을 페이징하여 조회할 수 있다")
    void execute_givenTwoKeywordsAndPageable_returnsMatchingBooks() {
        // given
        String firstKeyword = "java";
        String secondKeyword = "spring";
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Book> bookPage = loadBooksByAnyKeywordUseCase.execute(firstKeyword, secondKeyword, pageable);

        // then
        assertThat(bookPage.getNumber()).isEqualTo(pageable.getPageNumber());
        assertThat(bookPage.getSize()).isEqualTo(pageable.getPageSize());
        assertThat(bookPage.getContent().stream()
                .anyMatch(book ->
                        book.getBasicInfo().getTitle().toLowerCase().contains(firstKeyword) ||
                                book.getBasicInfo().getSubtitle().toLowerCase().contains(firstKeyword) ||
                                book.getBasicInfo().getTitle().toLowerCase().contains(secondKeyword) ||
                                book.getBasicInfo().getSubtitle().toLowerCase().contains(secondKeyword)))
                .isTrue();
    }
}
