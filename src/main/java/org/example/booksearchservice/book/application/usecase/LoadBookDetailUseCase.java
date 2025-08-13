package org.example.booksearchservice.book.application.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booksearchservice.book.application.port.LoadBookPort;
import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.book.exception.BookNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadBookDetailUseCase {

    private final LoadBookPort loadBookPort;

    @Transactional(readOnly = true)
    public Book execute(Long id) {
        log.info("Loading book details for Book ID: [{}]", id);
        return loadBookPort.loadById(id)
                .orElseThrow(BookNotFoundException::new);
    }
}
