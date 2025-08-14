package org.example.booksearchservice.book.application.service;

import org.example.booksearchservice.book.application.dto.BookDetailResponse;
import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.book.application.usecase.LoadBookDetailUseCase;
import org.example.booksearchservice.book.application.usecase.LoadBooksByAnyKeywordUseCase;
import org.example.booksearchservice.book.application.usecase.LoadBooksByKeywordExcludingUseCase;
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
import static org.example.booksearchservice.fixture.BookFixture.createBooks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookQueryServiceTest {

    private LoadBookDetailUseCase loadBookUseCase;
    private LoadBooksByKeywordUseCase loadBooksByKeywordUseCase;
    private LoadBooksByAnyKeywordUseCase loadBooksByAnyKeywordUseCase;
    private LoadBooksByKeywordExcludingUseCase loadBooksByKeywordExcludingUseCase;
    private BookQueryService bookQueryService;

    @BeforeEach
    void setUp() {
        loadBookUseCase = mock(LoadBookDetailUseCase.class);
        loadBooksByKeywordUseCase = mock(LoadBooksByKeywordUseCase.class);
        loadBooksByAnyKeywordUseCase = mock(LoadBooksByAnyKeywordUseCase.class);
        loadBooksByKeywordExcludingUseCase = mock(LoadBooksByKeywordExcludingUseCase.class);
        bookQueryService = new BookQueryService(loadBookUseCase, loadBooksByKeywordUseCase, loadBooksByAnyKeywordUseCase, loadBooksByKeywordExcludingUseCase);
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
    @DisplayName("[SUCCESS] 두 개의 키워드와 페이징 정보로 책 목록을 조회하면 페이지 응답을 반환하고, 적어도 하나의 키워드를 포함한다")
    void findBooksByAnyKeyword_givenTwoKeywordsAndPageable_returnsBookPageResponseContainingAnyKeyword() {
        // given
        String firstKeyword = "java";
        String secondKeyword = "spring";
        Pageable pageable = PageRequest.of(0, 10);

        // 책 생성 후, firstKeyword 또는 secondKeyword 포함하는 책만 필터링
        List<Book> books = createBooks(5).stream()
                .filter(book -> {
                    String title = book.getBasicInfo().getTitle().toLowerCase();
                    String subtitle = book.getBasicInfo().getSubtitle().toLowerCase();
                    return title.contains(firstKeyword) || subtitle.contains(firstKeyword) ||
                            title.contains(secondKeyword) || subtitle.contains(secondKeyword);
                })
                .toList();

        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        when(loadBooksByAnyKeywordUseCase.execute(firstKeyword, secondKeyword, pageable)).thenReturn(bookPage);

        // when
        BookPageResponse response = bookQueryService.findBooksByAnyKeyword(firstKeyword, secondKeyword, pageable);

        // then
        response.books().forEach(bookDto -> {
            String title = bookDto.title().toLowerCase();
            String subtitle = bookDto.subtitle().toLowerCase();
            boolean containsAnyKeyword = title.contains(firstKeyword) || subtitle.contains(firstKeyword) ||
                    title.contains(secondKeyword) || subtitle.contains(secondKeyword);

            assertThat(containsAnyKeyword).isTrue();
        });
    }

    @Test
    @DisplayName("[SUCCESS] 첫 번째 키워드를 포함하고 두 번째 키워드를 제외한 책 목록 조회 시 페이지 응답을 반환하고, 두 번째 키워드가 포함된 책은 없다")
    void findBooksByKeywordExcluding_givenTwoKeywordsAndPageable_returnsBookPageResponseWithoutSecondKeyword() {
        // given
        String firstKeyword = "spring";
        String secondKeyword = "java";
        Pageable pageable = PageRequest.of(0, 10);

        // 책 5권 생성 후, 첫 번째 키워드 포함 && 두 번째 키워드 미포함 조건을 만족하는 책만 필터링
        List<Book> books = createBooks(5).stream()
                .filter(book -> {
                    String title = book.getBasicInfo().getTitle().toLowerCase();
                    String subtitle = book.getBasicInfo().getSubtitle().toLowerCase();
                    return (title.contains(firstKeyword) || subtitle.contains(firstKeyword)) &&
                            !(title.contains(secondKeyword) || subtitle.contains(secondKeyword));
                })
                .toList();

        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        when(loadBooksByKeywordExcludingUseCase.execute(firstKeyword, secondKeyword, pageable)).thenReturn(bookPage);

        // when
        BookPageResponse response = bookQueryService.findBooksByKeywordExcluding(firstKeyword, secondKeyword, pageable);

        // then
        response.books().forEach(bookDto -> {
            assertThat(bookDto.title().toLowerCase()).doesNotContain(secondKeyword.toLowerCase());
            assertThat(bookDto.subtitle().toLowerCase()).doesNotContain(secondKeyword.toLowerCase());
        });
    }
}
