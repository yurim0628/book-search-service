package org.example.booksearchservice.search.application.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booksearchservice.search.application.port.UpdatePopularKeywordPort;
import org.example.booksearchservice.search.domain.SearchKeyword;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatePopularKeywordUseCase {

    private final UpdatePopularKeywordPort updatePopularKeywordPort;

    @Async
    public void execute(SearchKeyword keyword) {
        updatePopularKeywordPort.incrementCount(keyword.getValue());
        log.info("Successfully incremented popular keyword: [{}]", keyword);
    }
}
