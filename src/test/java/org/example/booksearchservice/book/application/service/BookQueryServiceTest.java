package org.example.booksearchservice.book.application.service;

import org.example.booksearchservice.book.application.dto.BookDetailResponse;
import org.example.booksearchservice.book.application.usecase.LoadBookDetailUseCase;
import org.example.booksearchservice.book.domain.BasicInfo;
import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.book.domain.PublicationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookQueryServiceTest {

    private LoadBookDetailUseCase loadBookUseCase;
    private BookQueryService bookQueryService;

    @BeforeEach
    void setUp() {
        loadBookUseCase = mock(LoadBookDetailUseCase.class);
        bookQueryService = new BookQueryService(loadBookUseCase);
    }

    @Test
    @DisplayName("[SUCCESS] 유효한 책 ID로 조회하면 BookDetailResponse를 반환한다")
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

    private Book createBook() {
        BasicInfo basicInfo = BasicInfo.builder()
                .title("Effective Java")
                .subtitle("Programming Language Guide")
                .author("Joshua Bloch")
                .build();

        PublicationInfo pubInfo = PublicationInfo.builder()
                .publisher("Addison-Wesley")
                .publishedDate(LocalDate.of(2018, 1, 11))
                .build();

        return Book.builder()
                .isbn("978-3-16-148410-0")
                .basicInfo(basicInfo)
                .publicationInfo(pubInfo)
                .build();
    }
}
