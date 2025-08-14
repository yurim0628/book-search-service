package org.example.booksearchservice.book.application.service;

import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.book.application.dto.BookDetailResponse;
import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.book.application.usecase.LoadBookDetailUseCase;
import org.example.booksearchservice.book.application.usecase.LoadBooksByAnyKeywordUseCase;
import org.example.booksearchservice.book.application.usecase.LoadBooksByKeywordUseCase;
import org.example.booksearchservice.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookQueryService {

    private final LoadBookDetailUseCase loadBookDetailUseCase;
    private final LoadBooksByKeywordUseCase loadBooksByKeywordUseCase;
    private final LoadBooksByAnyKeywordUseCase loadBooksByAnyKeywordUseCase;

    public BookDetailResponse getBookDetail(Long id) {
        Book book = loadBookDetailUseCase.execute(id);
        return BookDetailResponse.of(book);
    }

    public BookPageResponse findBooksByKeyword(String keyword, Pageable pageable) {
        Page<Book> bookPage = loadBooksByKeywordUseCase.execute(keyword, pageable);
        return BookPageResponse.of(bookPage);
    }

    public BookPageResponse findBooksByAnyKeyword(String firstKeyword, String secondKeyword, Pageable pageable) {
        Page<Book> bookPage = loadBooksByAnyKeywordUseCase.execute(firstKeyword, secondKeyword, pageable);
        return BookPageResponse.of(bookPage);
    }
}
