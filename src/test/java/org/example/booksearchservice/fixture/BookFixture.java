package org.example.booksearchservice.fixture;

import org.example.booksearchservice.book.domain.BasicInfo;
import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.book.domain.PublicationInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

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

    public static List<Book> createBooks(int count) {
        List<String> titles = List.of(
                "Effective Java",
                "Clean Code",
                "Java Concurrency in Practice",
                "Spring in Action",
                "Domain-Driven Design"
        );
        List<String> subtitles = List.of(
                "Programming Language Guide",
                "A Handbook of Agile Software Craftsmanship",
                "Concurrency Concepts and Examples",
                "Comprehensive Guide to Spring Framework",
                "Tackling Complexity in the Heart of Software"
        );
        List<String> authors = List.of(
                "Joshua Bloch",
                "Robert C. Martin",
                "Brian Goetz",
                "Craig Walls",
                "Eric Evans"
        );

        return IntStream.range(0, count)
                .mapToObj(i -> Book.builder()
                        .isbn("978-3-16-1484" + (100 + i) + "-0")
                        .basicInfo(BasicInfo.builder()
                                .title(titles.get(i % titles.size()))
                                .subtitle(subtitles.get(i % subtitles.size()))
                                .author(authors.get(i % authors.size()))
                                .build())
                        .publicationInfo(PublicationInfo.builder()
                                .publisher("Publisher " + (i + 1))
                                .publishedDate(LocalDate.of(2018, 1, 11).plusDays(i))
                                .build())
                        .build())
                .toList();
    }
}
