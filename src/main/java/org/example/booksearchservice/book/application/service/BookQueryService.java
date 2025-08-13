package org.example.booksearchservice.book.application.service;

import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.book.application.dto.BookDetailResponse;
import org.example.booksearchservice.book.application.usecase.LoadBookDetailUseCase;
import org.example.booksearchservice.book.domain.Book;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookQueryService {

    private final LoadBookDetailUseCase loadBookDetailUseCase;

    public BookDetailResponse getBookDetail(Long id) {
        Book book = loadBookDetailUseCase.execute(id);
        return BookDetailResponse.from(book);
    }
}
