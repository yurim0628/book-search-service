package org.example.booksearchservice.search.service;

import org.example.booksearchservice.search.application.service.SearchQueryParser;
import org.example.booksearchservice.search.exception.InvalidSearchQueryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.booksearchservice.common.exception.ErrorCode.INVALID_KEYWORD_COUNT;
import static org.example.booksearchservice.common.exception.ErrorCode.UNSUPPORTED_OPERATOR;

class SearchQueryParserTest {

    private final SearchQueryParser searchQueryParser = new SearchQueryParser();

    @Test
    @DisplayName("[ERROR] 지원하지 않는 연산자를 사용한 복합 쿼리는 예외를 발생시킨다")
    void parse_givenUnsupportedOperator_throwsException() {
        // given
        String invalidQuery = "java&spring";

        // when & then
        assertThatThrownBy(() -> searchQueryParser.parse(invalidQuery))
                .isInstanceOf(InvalidSearchQueryException.class)
                .hasMessageContaining(UNSUPPORTED_OPERATOR.getMessage());
    }

    @Test
    @DisplayName("[ERROR] 키워드가 비어 있는 복합 쿼리는 예외를 발생시킨다")
    void parse_givenEmptyKeyword_throwsException() {
        // given
        String query = "java|";

        // when & then
        assertThatThrownBy(() -> searchQueryParser.parse(query))
                .isInstanceOf(InvalidSearchQueryException.class)
                .hasMessageContaining(INVALID_KEYWORD_COUNT.getMessage());
    }
}
