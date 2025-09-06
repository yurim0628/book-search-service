package org.example.booksearchservice.search.application.service;

import org.example.booksearchservice.search.domain.SearchKeyword;
import org.example.booksearchservice.search.domain.SearchOperator;
import org.example.booksearchservice.search.domain.SearchQuery;
import org.example.booksearchservice.search.exception.InvalidSearchQueryException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.booksearchservice.common.exception.ErrorCode.INVALID_KEYWORD_COUNT;

@Component
public class SearchQueryParser {

    private static final int REQUIRED_KEYWORD_COUNT = 2;

    public SearchQuery parse(String query) {
        SearchOperator operator = SearchOperator.fromQuery(query);
        List<SearchKeyword> keywords = parseAndCreateKeywords(query, operator);

        if (keywords.size() != REQUIRED_KEYWORD_COUNT) {
            throw new InvalidSearchQueryException(INVALID_KEYWORD_COUNT);
        }

        return SearchQuery.of(keywords.getFirst(), keywords.getLast(), operator);
    }

    private List<SearchKeyword> parseAndCreateKeywords(String query, SearchOperator operator) {
        return Arrays.stream(query.split(operator.getSplitRegex()))
                .map(String::trim)
                .filter(keyword -> !keyword.isEmpty())
                .map(SearchKeyword::of)
                .collect(Collectors.toList());
    }
}
