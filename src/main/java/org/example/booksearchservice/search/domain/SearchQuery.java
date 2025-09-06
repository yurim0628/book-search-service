package org.example.booksearchservice.search.domain;

import lombok.Value;
import org.example.booksearchservice.search.exception.InvalidSearchQueryException;

import static org.example.booksearchservice.common.exception.ErrorCode.INVALID_KEYWORD_COUNT;

@Value
public class SearchQuery {

    SearchKeyword firstKeyword;
    SearchKeyword secondKeyword;
    SearchOperator operator;

    private SearchQuery(SearchKeyword first, SearchKeyword second, SearchOperator operator) {
        this.firstKeyword = first;
        this.secondKeyword = second;
        this.operator = operator;
    }

    /**
     * 주어진 두 개의 SearchKeyword 객체와 연산자로부터 SearchQuery 객체를 생성한다.
     * 주어진 키워드는 반드시 null이 아니어야 하며, 그렇지 않으면 예외를 던진다.
     */
    public static SearchQuery of(SearchKeyword first, SearchKeyword second, SearchOperator operator) {
        if (first == null || second == null) {
            throw new InvalidSearchQueryException(INVALID_KEYWORD_COUNT);
        }
        return new SearchQuery(first, second, operator);
    }
}
