package org.example.booksearchservice.book.application.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booksearchservice.book.application.port.LoadBookPort;
import org.example.booksearchservice.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadBooksByAnyKeywordUseCase {

    private final LoadBookPort loadBookPort;

    public Page<Book> execute(String firstKeyword, String secondKeyword, Pageable pageable) {
        log.info("Loading books with firstKeyword: [{}] secondKeyword [{}], page: [{}], size: [{}]",
                firstKeyword, secondKeyword, pageable.getPageNumber(), pageable.getPageSize());
        return loadBookPort.loadByAnyKeywords(firstKeyword, secondKeyword, pageable);
    }
}
