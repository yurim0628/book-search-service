package org.example.booksearchservice.search.application.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.search.application.port.BookInternalPort;
import org.example.booksearchservice.search.domain.SearchKeyword;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordSearchUseCase {

    private final BookInternalPort bookInternalPort;

    @Transactional(readOnly = true)
    public BookPageResponse execute(SearchKeyword keyword, Pageable pageable) {
        log.info("Executing keyword search for keyword: [{}]", keyword.getValue());
        return bookInternalPort.findBooksByKeyword(keyword, pageable);
    }
}
