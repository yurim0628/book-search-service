package org.example.booksearchservice.search.application.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.search.application.port.BookInternalPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("NOT")
@RequiredArgsConstructor
public class NotSearchUseCase implements OperatorSearchUseCase {

    private final BookInternalPort bookInternalPort;

    @Override
    @Transactional(readOnly = true)
    public BookPageResponse execute(String firstKeyword, String secondKeyword, Pageable pageable) {
        log.info("Executing NOT search with firstKeyword: [{}], excluding secondKeyword: [{}]",
                firstKeyword, secondKeyword);
        return bookInternalPort.findBooksByKeywordExcluding(firstKeyword, secondKeyword, pageable);
    }
}
