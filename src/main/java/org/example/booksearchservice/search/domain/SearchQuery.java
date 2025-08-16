package org.example.booksearchservice.search.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.booksearchservice.search.exception.InvalidSearchQueryException;

import java.util.List;

import static org.example.booksearchservice.common.exception.ErrorCode.INVALID_KEYWORD_COUNT;

@Getter
public class SearchQuery {

    private final String firstKeyword;
    private final String secondKeyword;
    private final SearchOperator operator;

    @Builder
    private SearchQuery(List<String> keywords, SearchOperator operator) {
        if(keywords.size() != 2) {
            throw new InvalidSearchQueryException(INVALID_KEYWORD_COUNT);
        }
        this.firstKeyword = keywords.getFirst();
        this.secondKeyword = keywords.getLast();
        this.operator = operator;
    }
}
