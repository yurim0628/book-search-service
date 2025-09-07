package org.example.booksearchservice.search.application.service;

import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.search.application.dto.SearchMetaData;
import org.example.booksearchservice.search.application.dto.SearchResponse;
import org.example.booksearchservice.search.application.usecase.KeywordSearchUseCase;
import org.example.booksearchservice.search.application.usecase.OperatorSearchUseCase;
import org.example.booksearchservice.search.application.usecase.UpdatePopularKeywordUseCase;
import org.example.booksearchservice.search.domain.SearchKeyword;
import org.example.booksearchservice.search.domain.SearchOperator;
import org.example.booksearchservice.search.domain.SearchQuery;
import org.example.booksearchservice.search.presentation.dto.ComplexSearchRequest;
import org.example.booksearchservice.search.presentation.dto.SimpleSearchRequest;
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

    public SearchResponse searchBooksByKeyword(SimpleSearchRequest request, Pageable pageable) {
        String keywordValue = request.keyword();
        SearchKeyword searchKeyword = SearchKeyword.of(keywordValue);

        BookPageResponse bookPageResponse = keywordSearchUseCase.execute(searchKeyword, pageable);
        updatePopularKeywordUseCase.execute(searchKeyword);

        return SearchResponse.of(
                keywordValue,
                bookPageResponse.pageInfo(),
                bookPageResponse.books(),
                SearchMetaData.ofDefaultExecutionTime(NONE)
        );
    }

    public SearchResponse searchBooksByComplexQuery(ComplexSearchRequest request, Pageable pageable) {
        String rawQuery = request.query();
        SearchQuery searchQuery = searchQueryParser.parse(rawQuery);
        SearchOperator searchOperator = searchQuery.getOperator();

        OperatorSearchUseCase operatorSearchUseCase = operatorSearchUseCaseMap.get(searchOperator.name());
        BookPageResponse bookPageResponse = operatorSearchUseCase.execute(
                searchQuery.getFirstKeyword(),
                searchQuery.getSecondKeyword(),
                pageable
        );

        updatePopularKeywordUseCase.execute(searchQuery.getFirstKeyword());

        return SearchResponse.of(
                rawQuery,
                bookPageResponse.pageInfo(),
                bookPageResponse.books(),
                SearchMetaData.ofDefaultExecutionTime(searchOperator)
        );
    }
}
