package org.example.booksearchservice.book.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.booksearchservice.book.domain.Book;

import java.time.LocalDate;

public record BookDetailResponse(
        String isbn,
        String title,
        String subtitle,
        String author,
        String publisher,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate published
) {
    public static BookDetailResponse of(Book book) {
        return new BookDetailResponse(
                book.getIsbn(),
                book.getBasicInfo().getTitle(),
                book.getBasicInfo().getSubtitle(),
                book.getBasicInfo().getAuthor(),
                book.getPublicationInfo().getPublisher(),
                book.getPublicationInfo().getPublishedDate()
        );
    }
}
