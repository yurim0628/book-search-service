package org.example.booksearchservice.search.application.service;

import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.search.application.dto.SearchMetaData;
import org.example.booksearchservice.search.application.dto.SearchResponse;
import org.example.booksearchservice.search.application.usecase.KeywordSearchUseCase;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.example.booksearchservice.search.domain.SearchOperator.NONE;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final KeywordSearchUseCase keywordSearchUseCase;

    public SearchResponse searchBooksByKeyword(String keyword, Pageable pageable) {
        BookPageResponse bookPageResponse = keywordSearchUseCase.execute(keyword, pageable);
        return SearchResponse.of(
                keyword,
                bookPageResponse.pageInfo(),
                bookPageResponse.books(),
                SearchMetaData.ofDefaultExecutionTime(NONE)
        );
    }
}
