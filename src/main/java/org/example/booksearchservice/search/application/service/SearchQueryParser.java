package org.example.booksearchservice.search.application.service;

import org.example.booksearchservice.search.domain.SearchOperator;
import org.example.booksearchservice.search.domain.SearchQuery;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SearchQueryParser {

    public SearchQuery parse(String query) {
        SearchOperator operator = SearchOperator.fromQuery(query);
        List<String> keywords = parseKeywords(query, operator);

        return SearchQuery.builder()
                .keywords(keywords)
                .operator(operator)
                .build();
    }

    private List<String> parseKeywords(String query, SearchOperator operator) {
        return Arrays.stream(query.split(operator.getSplitRegex()))
                .map(String::trim)
                .filter(keyword -> !keyword.isEmpty())
                .toList();
    }
}
