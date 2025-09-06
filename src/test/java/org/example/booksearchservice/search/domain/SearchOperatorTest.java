package org.example.booksearchservice.search.domain;

import org.example.booksearchservice.search.exception.InvalidSearchQueryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.booksearchservice.common.exception.ErrorCode.UNSUPPORTED_OPERATOR;

@DisplayName("SearchOperator 도메인 테스트")
class SearchOperatorTest {

    @Test
    @DisplayName("[SUCCESS] fromQuery 메서드는 쿼리 문자열에 포함된 연산자를 올바르게 판별한다")
    void fromQuery_withOrSymbol_returnsOrOperator() {
        SearchOperator op = SearchOperator.fromQuery("java|spring");
        assertThat(op).isEqualTo(SearchOperator.OR);
    }

    @Test
    @DisplayName("[SUCCESS] fromQuery 메서드는 쿼리 문자열에 포함된 연산자를 올바르게 판별한다")
    void fromQuery_withNotSymbol_returnsNotOperator() {
        SearchOperator op = SearchOperator.fromQuery("java-spring");
        assertThat(op).isEqualTo(SearchOperator.NOT);
    }

    @Test
    @DisplayName("[ERROR] fromQuery 메서드는 지원되지 않는 연산자가 포함된 경우 예외를 던진다")
    void fromQuery_withNoOperator_throwsException() {
        assertThatThrownBy(() -> SearchOperator.fromQuery("javaspring"))
                .isInstanceOf(InvalidSearchQueryException.class)
                .hasMessageContaining(UNSUPPORTED_OPERATOR.getMessage());
    }

    @Test
    @DisplayName("[ERROR] fromQuery 메서드는 지원되지 않는 연산자가 포함된 경우 예외를 던진다")
    void fromQuery_withUnsupportedOperator_throwsException() {
        assertThatThrownBy(() -> SearchOperator.fromQuery("java&spring"))
                .isInstanceOf(InvalidSearchQueryException.class)
                .hasMessageContaining(UNSUPPORTED_OPERATOR.getMessage());
    }
}
