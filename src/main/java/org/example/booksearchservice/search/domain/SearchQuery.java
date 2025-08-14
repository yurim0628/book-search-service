package org.example.booksearchservice.search.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchQuery {

    private final String firstKeyword;
    private final String secondKeyword;
    private final SearchOperator operator;

    @Builder
    private SearchQuery(String firstKeyword, String secondKeyword, SearchOperator operator) {
        this.firstKeyword = firstKeyword;
        this.secondKeyword = secondKeyword;
        this.operator = operator;
    }
}
