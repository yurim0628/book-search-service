package org.example.booksearchservice.search.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.search.exception.InvalidSearchQueryException;

import java.util.Arrays;

import static org.example.booksearchservice.common.exception.ErrorCode.UNSUPPORTED_OPERATOR;

@Getter
@RequiredArgsConstructor
public enum SearchOperator {
    NONE("", ""),
    OR("|", "\\|"),
    NOT("-", "-");

    private final String querySymbol;
    private final String splitRegex;

    public static SearchOperator fromQuery(String query) {
        return Arrays.stream(values())
                .filter(SearchOperator::isNotNone)
                .filter(operator -> operator.containsQuerySymbol(query))
                .findAny()
                .orElseThrow(() -> new InvalidSearchQueryException(UNSUPPORTED_OPERATOR));
    }

    private boolean isNotNone() {
        return this != NONE;
    }

    private boolean containsQuerySymbol(String query) {
        return query.contains(this.querySymbol);
    }
}
