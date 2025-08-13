package org.example.booksearchservice.book.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.booksearchservice.book.domain.BasicInfo;
import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.book.domain.PublicationInfo;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "books")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class BookEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String isbn;

    private String title;
    private String subtitle;
    private String image;
    private String author;

    private String publisher;
    private LocalDate publishedDate;

    public Book toDomain() {
        return Book.builder()
                .isbn(this.isbn)
                .basicInfo(BasicInfo.builder()
                        .title(this.title)
                        .subtitle(this.subtitle)
                        .author(this.author)
                         .build())
                .publicationInfo(PublicationInfo.builder()
                        .publisher(this.publisher)
                        .publishedDate(this.publishedDate)
                        .build())
                .build();
    }

    public static BookEntity fromDomain(Book book) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.isbn = book.getIsbn();
        bookEntity.title = book.getBasicInfo().getTitle();
        bookEntity.subtitle = book.getBasicInfo().getSubtitle();
        bookEntity.author = book.getBasicInfo().getAuthor();
        bookEntity.publisher = book.getPublicationInfo().getPublisher();
        bookEntity.publishedDate = book.getPublicationInfo().getPublishedDate();
        return bookEntity;
    }
}
