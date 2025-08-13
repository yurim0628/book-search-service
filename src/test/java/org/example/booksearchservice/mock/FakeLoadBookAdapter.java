package org.example.booksearchservice.mock;

import org.example.booksearchservice.book.application.port.LoadBookPort;
import org.example.booksearchservice.book.domain.BasicInfo;
import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.book.domain.PublicationInfo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeLoadBookAdapter implements LoadBookPort {

    private final Map<Long, Book> store = new HashMap<>();

    public FakeLoadBookAdapter() {
        store.put(1L, Book.builder()
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
                .build());
        store.put(2L, Book.builder()
                .isbn("978-0-13-468609-7")
                .basicInfo(BasicInfo.builder()
                        .title("Clean Code")
                        .subtitle("A Handbook of Agile Software Craftsmanship")
                        .author("Robert C. Martin")
                        .build())
                .publicationInfo(PublicationInfo.builder()
                        .publisher("Prentice Hall")
                        .publishedDate(LocalDate.of(2008, 8, 1))
                        .build())
                .build());
    }

    @Override
    public Optional<Book> loadById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
}
