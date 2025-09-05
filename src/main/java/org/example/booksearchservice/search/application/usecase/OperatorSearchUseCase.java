package org.example.booksearchservice.search.application.usecase;

import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.search.domain.SearchKeyword;
import org.springframework.data.domain.Pageable;

public interface OperatorSearchUseCase {
    BookPageResponse execute(SearchKeyword firstKeyword, SearchKeyword secondKeyword, Pageable pageable);
}

