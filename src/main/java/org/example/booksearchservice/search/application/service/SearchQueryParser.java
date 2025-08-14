package org.example.booksearchservice.search.application.service;

import org.example.booksearchservice.search.domain.SearchOperator;
import org.example.booksearchservice.search.domain.SearchQuery;
import org.example.booksearchservice.search.exception.InvalidSearchQueryException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.example.booksearchservice.common.exception.ErrorCode.INVALID_KEYWORD_COUNT;
import static org.example.booksearchservice.common.exception.ErrorCode.UNSUPPORTED_OPERATOR;

@Component
public class SearchQueryParser {

    public SearchQuery parse(String query) {
        SearchOperator operator = extractOperator(query);
        List<String> keywords = extractKeywords(query, operator);
        validateKeywords(keywords);
        return buildSearchQuery(keywords, operator);
    }

    private SearchOperator extractOperator(String query) {
        return SearchOperator.fromQuery(query)
                .orElseThrow(() -> new InvalidSearchQueryException(UNSUPPORTED_OPERATOR));
    }

    private List<String> extractKeywords(String query, SearchOperator operator) {
        return Arrays.stream(query.split(operator.getSplitRegex()))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    private void validateKeywords(List<String> keywords) {
        if (keywords.size() != 2) {
            throw new InvalidSearchQueryException(INVALID_KEYWORD_COUNT);
        }
    }

    private SearchQuery buildSearchQuery(List<String> keywords, SearchOperator operator) {
        return SearchQuery.builder()
                .firstKeyword(keywords.get(0))
                .secondKeyword(keywords.get(1))
                .operator(operator)
                .build();
    }
}
