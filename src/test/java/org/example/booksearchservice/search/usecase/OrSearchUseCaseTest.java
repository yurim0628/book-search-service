package org.example.booksearchservice.search.usecase;

import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.mock.FakeBookInternalAdapter;
import org.example.booksearchservice.search.application.port.BookInternalPort;
import org.example.booksearchservice.search.application.usecase.OrSearchUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

class OrSearchUseCaseTest {

    private OrSearchUseCase orSearchUseCase;

    @BeforeEach
    void setUp() {
        BookInternalPort bookInternalPort = new FakeBookInternalAdapter();
        orSearchUseCase = new OrSearchUseCase(bookInternalPort);
    }

    @Test
    @DisplayName("[SUCCESS] OR 연산자로 두 키워드 중 하나라도 포함하는 책을 조회한다")
    void execute_givenTwoKeywords_returnsBooksMatchingEitherKeyword() {
        // given
        String firstKeyword = "java";
        String secondKeyword = "spring";
        Pageable pageable = PageRequest.of(0, 10);

        // when
        BookPageResponse response = orSearchUseCase.execute(firstKeyword, secondKeyword, pageable);

        // then
        assertThat(response.pageInfo().totalElements()).isEqualTo(3);
        assertThat(response.books().stream()
                .anyMatch(book ->
                        book.title().toLowerCase().contains(firstKeyword) ||
                                book.subtitle().toLowerCase().contains(firstKeyword) ||
                                book.title().toLowerCase().contains(secondKeyword) ||
                                book.subtitle().toLowerCase().contains(secondKeyword)))
                .isTrue();
    }

}
