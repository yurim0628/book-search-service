package org.example.booksearchservice.search.application.service;

import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.search.application.dto.SearchMetaData;
import org.example.booksearchservice.search.application.dto.SearchResponse;
import org.example.booksearchservice.search.application.usecase.OperatorSearchUseCase;
import org.example.booksearchservice.search.application.usecase.KeywordSearchUseCase;
import org.example.booksearchservice.search.application.usecase.UpdatePopularKeywordUseCase;
import org.example.booksearchservice.search.domain.SearchOperator;
import org.example.booksearchservice.search.domain.SearchQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.example.booksearchservice.search.domain.SearchOperator.NONE;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final KeywordSearchUseCase keywordSearchUseCase;
    private final Map<String, OperatorSearchUseCase> operatorSearchUseCaseMap;
    private final SearchQueryParser searchQueryParser;

    private final UpdatePopularKeywordUseCase updatePopularKeywordUseCase;

    public SearchResponse searchBooksByKeyword(String keyword, Pageable pageable) {
        BookPageResponse bookPageResponse = keywordSearchUseCase.execute(keyword, pageable);
        updatePopularKeywordUseCase.execute(keyword);
        return buildSearchResponse(keyword, bookPageResponse, NONE);
    }

    public SearchResponse searchBooksByComplexQuery(String query, Pageable pageable) {
        SearchQuery searchQuery = searchQueryParser.parse(query);
        SearchOperator searchOperator = searchQuery.getOperator();

        OperatorSearchUseCase operatorSearchUseCase = operatorSearchUseCaseMap.get(searchOperator.name());
        BookPageResponse bookPageResponse = operatorSearchUseCase.execute(
                searchQuery.getFirstKeyword(),
                searchQuery.getSecondKeyword(),
                pageable
        );

        updatePopularKeywordUseCase.execute(searchQuery.getFirstKeyword());

        return buildSearchResponse(query, bookPageResponse, searchOperator);
    }

    private SearchResponse buildSearchResponse(String query, BookPageResponse bookPageResponse, SearchOperator searchOperator) {
        return SearchResponse.of(
                query,
                bookPageResponse.pageInfo(),
                bookPageResponse.books(),
                SearchMetaData.ofDefaultExecutionTime(searchOperator)
        );
    }
}
