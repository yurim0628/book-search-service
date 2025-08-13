package org.example.booksearchservice.book.application.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booksearchservice.book.application.port.LoadBookPort;
import org.example.booksearchservice.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadBooksByKeywordUseCase {

    private final LoadBookPort loadBookPort;

    @Transactional(readOnly = true)
    public Page<Book> execute(String keyword, Pageable pageable) {
        log.info("Loading books with keyword: [{}], page: [{}], size: [{}]",
                keyword, pageable.getPageNumber(), pageable.getPageSize());
        return loadBookPort.loadByKeyword(keyword, pageable);
    }
}
