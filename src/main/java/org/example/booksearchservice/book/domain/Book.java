package org.example.booksearchservice.book.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Book {

    private final String isbn;
    private final BasicInfo basicInfo;
    private final PublicationInfo publicationInfo;

    @Builder
    private Book(String isbn, BasicInfo basicInfo, PublicationInfo publicationInfo) {
        this.isbn = isbn;
        this.basicInfo = basicInfo;
        this.publicationInfo = publicationInfo;
    }
}

