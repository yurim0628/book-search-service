package org.example.booksearchservice.search.application.dto;

import org.example.booksearchservice.search.domain.SearchOperator;

public record SearchMetaData(
        Long executionTime,
        SearchOperator strategy
) {
    public static SearchMetaData ofDefaultExecutionTime(SearchOperator strategy) {
        return new SearchMetaData(0L, strategy);
    }

    public static SearchMetaData of(Long executionTime, SearchOperator searchOperator) {
        return new SearchMetaData(
                executionTime,
                searchOperator
        );
    }
}
