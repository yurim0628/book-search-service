package org.example.booksearchservice.book.application.usecase;

import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.mock.FakeLoadBookAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;

class LoadBooksByKeywordExcludingUseCaseTest {

    private LoadBooksByKeywordExcludingUseCase useCase;

    @BeforeEach
    void setUp() {
        FakeLoadBookAdapter fakeLoadBookAdapter = new FakeLoadBookAdapter();
        useCase = new LoadBooksByKeywordExcludingUseCase(fakeLoadBookAdapter);
    }

    @Test
    @DisplayName("[SUCCESS] 첫 번째 키워드는 포함하고 두 번째 키워드는 제외하는 책 목록 조회")
    void execute_givenKeywords_returnsBooksExcludingSecondKeyword() {
        // given
        String firstKeyword = "java";
        String secondKeyword = "spring";
        PageRequest pageable = PageRequest.of(0, 10);

        // when
        Page<Book> result = useCase.execute(firstKeyword, secondKeyword, pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).allMatch(book ->
                (book.getBasicInfo().getTitle().toLowerCase().contains(firstKeyword.toLowerCase())
                        || book.getBasicInfo().getSubtitle().toLowerCase().contains(firstKeyword.toLowerCase()))
                        &&
                        !(book.getBasicInfo().getTitle().toLowerCase().contains(secondKeyword.toLowerCase())
                                || book.getBasicInfo().getSubtitle().toLowerCase().contains(secondKeyword.toLowerCase()))
        );
    }
}
