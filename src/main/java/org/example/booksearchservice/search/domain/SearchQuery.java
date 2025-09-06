package org.example.booksearchservice.search.domain;

import lombok.Value;
import org.example.booksearchservice.search.exception.InvalidSearchQueryException;

import java.util.List;

import static org.example.booksearchservice.common.exception.ErrorCode.INVALID_KEYWORD_COUNT;

@Value
public class SearchQuery {

    private static final int REQUIRED_KEYWORD_COUNT = 2;

    SearchKeyword firstKeyword;
    SearchKeyword secondKeyword;
    SearchOperator operator;

    private SearchQuery(SearchKeyword firstKeyword, SearchKeyword secondKeyword, SearchOperator operator) {
        this.firstKeyword = firstKeyword;
        this.secondKeyword = secondKeyword;
        this.operator = operator;
    }

    /**
     * 주어진 키워드 리스트와 연산자로부터 SearchQuery 객체를 생성한다.
     * 키워드 리스트는 반드시 2개의 키워드를 포함해야 하며, 그렇지 않으면 예외를 던진다.
     */
    public static SearchQuery of(List<String> keywords, SearchOperator operator) {
        if (keywords == null || keywords.size() != REQUIRED_KEYWORD_COUNT) {
            throw new InvalidSearchQueryException(INVALID_KEYWORD_COUNT);
        }

        SearchKeyword first = SearchKeyword.of(keywords.getFirst());
        SearchKeyword second = SearchKeyword.of(keywords.getLast());

        return new SearchQuery(first, second, operator);
    }
}
