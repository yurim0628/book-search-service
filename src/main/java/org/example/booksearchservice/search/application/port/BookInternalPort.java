package org.example.booksearchservice.search.application.port;

import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.search.domain.SearchKeyword;
import org.springframework.data.domain.Pageable;

public interface BookInternalPort {
    BookPageResponse findBooksByKeyword(SearchKeyword keyword, Pageable pageable);
    BookPageResponse findBooksByAnyKeyword(SearchKeyword firstKeyword, SearchKeyword secondKeyword, Pageable pageable);
    BookPageResponse findBooksByKeywordExcluding(SearchKeyword firstKeyword, SearchKeyword secondKeyword, Pageable pageable);
}
