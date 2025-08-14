package org.example.booksearchservice.search.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum SearchOperator {
    NONE("", ""),
    OR("|", "\\|"),
    NOT("-", "-");

    private final String querySymbol;
    private final String splitRegex;

    public static Optional<SearchOperator> fromQuery(String query) {
        return Arrays.stream(values())
                .filter(op -> !op.equals(NONE) && query.contains(op.querySymbol))
                .findFirst();
    }
}

