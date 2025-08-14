package org.example.booksearchservice.book.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.book.application.port.LoadBookPort;
import org.example.booksearchservice.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LoadBookAdapter implements LoadBookPort {

    private final BookJpaRepository bookJpaRepository;

    @Override
    public Optional<Book> loadById(Long id) {
        return bookJpaRepository.findById(id)
                .map(BookEntity::toDomain);
    }

    @Override
    public Page<Book> loadByKeyword(String keyword, Pageable pageable) {
        return bookJpaRepository.findByKeyword(keyword, pageable)
                .map(BookEntity::toDomain);
    }

    @Override
    public Page<Book> loadByAnyKeywords(String firstKeyword, String secondKeyword, Pageable pageable) {
        return bookJpaRepository.findByAnyKeywords(firstKeyword, secondKeyword, pageable)
                .map(BookEntity::toDomain);
    }
}
