package org.example.booksearchservice.book.application.usecase;

import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.book.exception.BookNotFoundException;
import org.example.booksearchservice.mock.FakeLoadBookAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LoadBookUseCaseTest {

    private LoadBookDetailUseCase loadBookUseCase;

    @BeforeEach
    void setUp() {
        FakeLoadBookAdapter fakeLoadBookAdapter = new FakeLoadBookAdapter();
        loadBookUseCase = new LoadBookDetailUseCase(fakeLoadBookAdapter);
    }

    @Test
    @DisplayName("[SUCCESS] 유효한 ID로 책 상세 조회에 성공한다")
    void loadBookDetail_whenValidId_thenReturnBook() {
        // given
        Long bookId = 1L;

        // when
        Book book = loadBookUseCase.execute(bookId);

        // then
        assertThat(book.getIsbn()).isEqualTo("978-3-16-148410-0");
    }

    @Test
    @DisplayName("[FAILURE] 존재하지 않는 ID로 책 조회 시 BookNotFoundException 예외가 발생한다")
    void loadBookDetail_whenInvalidId_thenThrowBookNotFound() {
        // given
        Long bookId = 999L;

        // when & then
        assertThatThrownBy(() -> loadBookUseCase.execute(bookId))
                .isInstanceOf(BookNotFoundException.class);
    }
}
