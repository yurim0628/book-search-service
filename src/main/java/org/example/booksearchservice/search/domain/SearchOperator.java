package org.example.booksearchservice.search.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchOperator {
    NONE("", ""),
    OR("|", "\\|"),
    NOT("-", "-");

    private final String querySymbol;
    private final String splitRegex;
}
