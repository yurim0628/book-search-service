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
@Service("OR")
@RequiredArgsConstructor
public class OrSearchUseCase implements OperatorSearchUseCase {

    private final BookInternalPort bookInternalPort;

    @Override
    @Transactional(readOnly = true)
    public BookPageResponse execute(SearchKeyword firstKeyword, SearchKeyword secondKeyword, Pageable pageable) {
        log.info("Executing OR search with firstKeyword: [{}], secondKeyword: [{}]",
                firstKeyword, secondKeyword);
        return bookInternalPort.findBooksByAnyKeyword(firstKeyword.getValue(), secondKeyword.getValue(), pageable);
    }
}
