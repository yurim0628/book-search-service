package org.example.booksearchservice.search.usecase;

import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.mock.FakeBookInternalAdapter;
import org.example.booksearchservice.search.application.port.BookInternalPort;
import org.example.booksearchservice.search.application.usecase.NotSearchUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

class NotSearchUseCaseTest {

    private NotSearchUseCase notSearchUseCase;

    @BeforeEach
    void setUp() {
        BookInternalPort bookInternalPort = new FakeBookInternalAdapter();
        notSearchUseCase = new NotSearchUseCase(bookInternalPort);
    }

    @Test
    @DisplayName("[SUCCESS] NOT 연산자로 첫 번째 키워드는 포함하고 두 번째 키워드는 제외한 책을 조회한다")
    void execute_givenTwoKeywords_returnsBooksMatchingFirstKeywordExcludingSecondKeyword() {
        // given
        String firstKeyword = "java";
        String secondKeyword = "effective";
        Pageable pageable = PageRequest.of(0, 10);

        // when
        BookPageResponse response = notSearchUseCase.execute(firstKeyword, secondKeyword, pageable);

        // then
        assertThat(response.pageInfo().totalElements()).isEqualTo(1);
        assertThat(response.books())
                .allMatch(book ->
                        (book.title().toLowerCase().contains(firstKeyword) || book.subtitle().toLowerCase().contains(firstKeyword)) &&
                                !book.title().toLowerCase().contains(secondKeyword) && !book.subtitle().toLowerCase().contains(secondKeyword)
                );
    }
}
