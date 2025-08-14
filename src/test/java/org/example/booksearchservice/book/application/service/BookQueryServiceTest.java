package org.example.booksearchservice.book.application.service;

import org.example.booksearchservice.book.application.dto.BookDetailResponse;
import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.book.application.usecase.LoadBookDetailUseCase;
import org.example.booksearchservice.book.application.usecase.LoadBooksByAnyKeywordUseCase;
import org.example.booksearchservice.book.application.usecase.LoadBooksByKeywordUseCase;
import org.example.booksearchservice.book.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.example.booksearchservice.fixture.BookFixture.createBook;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookQueryServiceTest {

    private LoadBookDetailUseCase loadBookUseCase;
    private LoadBooksByKeywordUseCase loadBooksByKeywordUseCase;
    private LoadBooksByAnyKeywordUseCase loadBooksByAnyKeywordUseCase;
    private BookQueryService bookQueryService;

    @BeforeEach
    void setUp() {
        loadBookUseCase = mock(LoadBookDetailUseCase.class);
        loadBooksByKeywordUseCase = mock(LoadBooksByKeywordUseCase.class);
        loadBooksByAnyKeywordUseCase = mock(LoadBooksByAnyKeywordUseCase.class);
        bookQueryService = new BookQueryService(loadBookUseCase, loadBooksByKeywordUseCase, loadBooksByAnyKeywordUseCase);
    }

    @Test
    @DisplayName("[SUCCESS] 책 ID로 책 상세 정보를 조회하면 책 상세 응답을 반환한다")
    void getBookDetail_givenExistingBookId_returnsBookDetailResponse() {
        // given
        Long bookId = 1L;
        Book book = createBook();
        when(loadBookUseCase.execute(bookId)).thenReturn(book);

        // when
        BookDetailResponse response = bookQueryService.getBookDetail(bookId);

        // then
        assertThat(response.isbn()).isEqualTo(book.getIsbn());
    }


    @Test
    @DisplayName("[SUCCESS] 키워드와 페이징 정보를 이용해 책 목록을 조회하면 페이지 응답을 반환한다")
    void findBooksByKeyword_givenKeywordAndPageable_returnsBookPageResponse() {
        // given
        String keyword = "java";
        Pageable pageable = PageRequest.of(0, 10);

        List<Book> books = List.of(createBook());
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        when(loadBooksByKeywordUseCase.execute(keyword, pageable)).thenReturn(bookPage);

        // when
        BookPageResponse response = bookQueryService.findBooksByKeyword(keyword, pageable);

        // then
        assertThat(response.books().getFirst().isbn()).isEqualTo(books.getFirst().getIsbn());
    }

    @Test
    @DisplayName("[SUCCESS] 두 개의 키워드와 페이징 정보로 책 목록을 조회하면 페이지 응답을 반환한다")
    void findBooksByAnyKeyword_givenTwoKeywordsAndPageable_returnsBookPageResponse() {
        // given
        String firstKeyword = "java";
        String secondKeyword = "spring";
        Pageable pageable = PageRequest.of(0, 10);

        List<Book> books = List.of(createBook());
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        when(loadBooksByAnyKeywordUseCase.execute(firstKeyword, secondKeyword, pageable)).thenReturn(bookPage);

        // when
        BookPageResponse response = bookQueryService.findBooksByAnyKeyword(firstKeyword, secondKeyword, pageable);

        // then
        assertThat(response.books().getFirst().isbn()).isEqualTo(books.getFirst().getIsbn());
    }
}
