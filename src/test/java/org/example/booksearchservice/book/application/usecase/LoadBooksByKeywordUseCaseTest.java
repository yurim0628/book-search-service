package org.example.booksearchservice.book.application.usecase;

import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.mock.FakeLoadBookAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class LoadBooksByKeywordUseCaseTest {

    private LoadBooksByKeywordUseCase loadBooksByKeywordUseCase;

    @BeforeEach
    void setUp() {
        FakeLoadBookAdapter fakeLoadBookAdapter = new FakeLoadBookAdapter();
        loadBooksByKeywordUseCase = new LoadBooksByKeywordUseCase(fakeLoadBookAdapter);
    }

    @Test
    @DisplayName("[SUCCESS] 키워드와 페이징으로 도서 목록을 조회할 수 있다")
    void execute_givenKeywordAndPageable_returnsBooks() {
        // given
        String keyword = "java";
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Book> bookPage = loadBooksByKeywordUseCase.execute(keyword, pageable);

        // then
        assertThat(bookPage.getContent()).isNotNull();
        assertThat(bookPage.getNumber()).isEqualTo(pageable.getPageNumber());
        assertThat(bookPage.getSize()).isEqualTo(pageable.getPageSize());
    }
}

