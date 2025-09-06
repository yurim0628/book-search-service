package org.example.booksearchservice.search.infrastructure.internal;

import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.book.application.service.BookQueryService;
import org.example.booksearchservice.search.application.port.BookInternalPort;
import org.example.booksearchservice.search.domain.SearchKeyword;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookInternalAdapter implements BookInternalPort {

    private final BookQueryService bookQueryService;

    @Override
    public BookPageResponse findBooksByKeyword(SearchKeyword keyword, Pageable pageable) {
        return bookQueryService.findBooksByKeyword(keyword.getValue(), pageable);
    }

    @Override
    public BookPageResponse findBooksByAnyKeyword(SearchKeyword firstKeyword, SearchKeyword secondKeyword, Pageable pageable) {
        return bookQueryService.findBooksByAnyKeyword(firstKeyword.getValue(), secondKeyword.getValue(), pageable);
    }

    @Override
    public BookPageResponse findBooksByKeywordExcluding(SearchKeyword firstKeyword, SearchKeyword secondKeyword, Pageable pageable) {
        return bookQueryService.findBooksByKeywordExcluding(firstKeyword.getValue(), secondKeyword.getValue(), pageable);
    }
}
