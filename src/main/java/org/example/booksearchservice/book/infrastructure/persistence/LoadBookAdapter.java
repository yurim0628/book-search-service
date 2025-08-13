package org.example.booksearchservice.book.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.book.application.port.LoadBookPort;
import org.example.booksearchservice.book.domain.Book;
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
}
