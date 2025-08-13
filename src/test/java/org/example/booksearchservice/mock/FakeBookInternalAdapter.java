package org.example.booksearchservice.mock;

import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.search.application.port.BookInternalPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.example.booksearchservice.fixture.BookFixture.createBook;

public class FakeBookInternalAdapter implements BookInternalPort {

    @Override
    public BookPageResponse findBooksByKeyword(String keyword, Pageable pageable) {
        List<Book> books = List.of(createBook());
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        return BookPageResponse.of(bookPage);
    }
}
