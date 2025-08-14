package org.example.booksearchservice.search.application.usecase;

import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.springframework.data.domain.Pageable;

public interface OperatorSearchUseCase {
    BookPageResponse execute(String firstKeyword, String secondKeyword, Pageable pageable);
}

