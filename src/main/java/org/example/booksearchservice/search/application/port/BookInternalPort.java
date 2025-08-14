package org.example.booksearchservice.search.application.port;

import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.springframework.data.domain.Pageable;

public interface BookInternalPort {
    BookPageResponse findBooksByKeyword(String keyword, Pageable pageable);
    BookPageResponse findBooksByAnyKeyword(String firstKeyword, String secondKeyword, Pageable pageable);
}
