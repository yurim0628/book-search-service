package org.example.booksearchservice.search.application.dto;

import org.example.booksearchservice.book.application.dto.BookDetailResponse;
import org.example.booksearchservice.book.application.dto.PageInfo;

import java.util.List;

public record SearchResponse(
        String searchQuery,
        PageInfo pageInfo,
        List<BookDetailResponse> books,
        SearchMetaData searchMetaData
) {
    public static SearchResponse of(
            String searchQuery,
            PageInfo pageInfo,
            List<BookDetailResponse> books,
            SearchMetaData searchMetaData
    ) {
        return new SearchResponse(
                searchQuery,
                pageInfo,
                books,
                searchMetaData
        );
    }
}
