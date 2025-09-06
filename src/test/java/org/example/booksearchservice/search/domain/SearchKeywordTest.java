package org.example.booksearchservice.search.domain;

import org.example.booksearchservice.search.exception.InvalidSearchQueryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.booksearchservice.common.exception.ErrorCode.*;

@DisplayName("SearchKeyword 도메인 테스트")
class SearchKeywordTest {

    @Test
    @DisplayName("[SUCCESS] 유효한 키워드로 SearchKeyword 인스턴스를 생성한다")
    void validKeyword_createsInstance() {
        SearchKeyword keyword = SearchKeyword.of("Java Spring");
        assertThat(keyword.getValue()).isEqualTo("java spring");
    }

    @Test
    @DisplayName("[ERROR] null 키워드는 예외를 발생시킨다")
    void nullKeyword_throwsException() {
        assertThatThrownBy(() -> SearchKeyword.of(null))
                .isInstanceOf(InvalidSearchQueryException.class)
                .hasMessageContaining(KEYWORD_REQUIRED.getMessage());
    }

    @Test
    @DisplayName("[ERROR] 빈 문자열 키워드는 예외를 발생시킨다")
    void tooShortKeyword_throwsException() {
        assertThatThrownBy(() -> SearchKeyword.of("a"))
                .isInstanceOf(InvalidSearchQueryException.class)
                .hasMessageContaining(INVALID_KEYWORD_LENGTH.getMessage());
    }

    @Test
    @DisplayName("[ERROR] 50자를 초과하는 키워드는 예외를 발생시킨다")
    void tooLongKeyword_throwsException() {
        String longKeyword = "a".repeat(51);
        assertThatThrownBy(() -> SearchKeyword.of(longKeyword))
                .isInstanceOf(InvalidSearchQueryException.class)
                .hasMessageContaining(INVALID_KEYWORD_LENGTH.getMessage());
    }

    @Test
    @DisplayName("[ERROR] 특수 문자가 포함된 키워드는 예외를 발생시킨다")
    void invalidPatternKeyword_throwsException() {
        assertThatThrownBy(() -> SearchKeyword.of("java!@#"))
                .isInstanceOf(InvalidSearchQueryException.class)
                .hasMessageContaining(INVALID_KEYWORD_PATTERN.getMessage());
    }

    @Test
    @DisplayName("[SUCCESS] 여러 공백이 포함된 키워드는 단일 공백으로 정규화된다")
    void keywordWithMultipleSpaces_normalizesToSingleSpace() {
        SearchKeyword keyword = SearchKeyword.of("  Java   Spring   Boot  ");
        assertThat(keyword.getValue()).isEqualTo("java spring boot");
    }
}
