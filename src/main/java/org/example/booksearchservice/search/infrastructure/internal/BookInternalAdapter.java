package org.example.booksearchservice.search.infrastructure.internal;

import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.book.application.service.BookQueryService;
import org.example.booksearchservice.search.application.port.BookInternalPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookInternalAdapter implements BookInternalPort {

    private final BookQueryService bookQueryService;

    @Override
    public BookPageResponse findBooksByKeyword(String keyword, Pageable pageable) {
        return bookQueryService.findBooksByKeyword(keyword, pageable);
    }

    @Override
    public BookPageResponse findBooksByAnyKeyword(String firstKeyword, String secondKeyword, Pageable pageable) {
        return bookQueryService.findBooksByAnyKeyword(firstKeyword, secondKeyword, pageable);
    }
}
