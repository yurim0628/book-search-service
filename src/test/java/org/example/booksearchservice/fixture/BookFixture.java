package org.example.booksearchservice.fixture;

import org.example.booksearchservice.book.domain.BasicInfo;
import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.book.domain.PublicationInfo;

import java.time.LocalDate;

public class BookFixture {

    public static Book createBook() {
        return Book.builder()
                .isbn("978-3-16-148410-0")
                .basicInfo(BasicInfo.builder()
                        .title("Effective Java")
                        .subtitle("Programming Language Guide")
                        .author("Joshua Bloch")
                        .build())
                .publicationInfo(PublicationInfo.builder()
                        .publisher("Addison-Wesley")
                        .publishedDate(LocalDate.of(2018, 1, 11))
                        .build())
                .build();
    }
}
