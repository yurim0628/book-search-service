package org.example.booksearchservice.book.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PublicationInfo {

    private final String publisher;
    private final LocalDate publishedDate;

    @Builder
    private PublicationInfo(String publisher, LocalDate publishedDate) {
        this.publisher = publisher;
        this.publishedDate = publishedDate;
    }
}
