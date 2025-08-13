package org.example.booksearchservice.book.application.dto;

import org.example.booksearchservice.book.domain.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public record BookPageResponse(
        PageInfo pageInfo,
        List<BookDetailResponse> books
) {
    public static BookPageResponse of(Page<Book> bookPage) {
        return new BookPageResponse(
                PageInfo.of(bookPage),
                bookPage.getContent().stream()
                        .map(BookDetailResponse::of)
                        .toList()
        );
    }
}
