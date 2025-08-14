package org.example.booksearchservice.book.application.port;

import org.example.booksearchservice.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoadBookPort {
    Optional<Book> loadById(Long id);
    Page<Book> loadByKeyword(String keyword, Pageable pageable);
    Page<Book> loadByAnyKeywords(String firstKeyword, String secondKeyword, Pageable pageable);
    Page<Book> loadByKeywordExcluding(String firstKeyword, String secondKeyword, Pageable pageable);
}
