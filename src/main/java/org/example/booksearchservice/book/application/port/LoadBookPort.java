package org.example.booksearchservice.book.application.port;

import org.example.booksearchservice.book.domain.Book;

import java.util.Optional;

public interface LoadBookPort {
    Optional<Book> loadById(Long id);
}
