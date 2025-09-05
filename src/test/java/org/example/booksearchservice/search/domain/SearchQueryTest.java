package org.example.booksearchservice.search.domain;

import org.example.booksearchservice.search.exception.InvalidSearchQueryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.booksearchservice.common.exception.ErrorCode.INVALID_KEYWORD_COUNT;

@DisplayName("SearchQuery 도메인 테스트")
class SearchQueryTest {

    @Test
    @DisplayName("[SUCCESS] 유효한 키워드와 연산자로 SearchQuery 인스턴스를 생성한다")
    void validKeywords_createsInstance() {
        SearchQuery query = SearchQuery.of(List.of("Java", "Spring"), SearchOperator.OR);
        assertThat(query.getFirstKeyword().getValue()).isEqualTo("java");
        assertThat(query.getSecondKeyword().getValue()).isEqualTo("spring");
        assertThat(query.getOperator()).isEqualTo(SearchOperator.OR);
    }

    @Test
    @DisplayName("[ERROR] null 키워드 리스트는 예외를 발생시킨다")
    void nullKeywords_throwsException() {
        assertThatThrownBy(() -> SearchQuery.of(null, SearchOperator.OR))
                .isInstanceOf(InvalidSearchQueryException.class)
                .hasMessageContaining(INVALID_KEYWORD_COUNT.getMessage());
    }

    @Test
    @DisplayName("[ERROR] 2개 미만의 키워드는 예외를 발생시킨다")
    void lessThanTwoKeywords_throwsException() {
        assertThatThrownBy(() -> SearchQuery.of(List.of("Java"), SearchOperator.OR))
                .isInstanceOf(InvalidSearchQueryException.class)
                .hasMessageContaining(INVALID_KEYWORD_COUNT.getMessage());
    }

    @Test
    @DisplayName("[ERROR] 2개 초과의 키워드는 예외를 발생시킨다")
    void moreThanTwoKeywords_throwsException() {
        assertThatThrownBy(() -> SearchQuery.of(List.of("Java", "Spring", "Boot"), SearchOperator.OR))
                .isInstanceOf(InvalidSearchQueryException.class)
                .hasMessageContaining(INVALID_KEYWORD_COUNT.getMessage());
    }
}
