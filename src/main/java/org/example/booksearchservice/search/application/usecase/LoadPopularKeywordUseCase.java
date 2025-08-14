package org.example.booksearchservice.search.application.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booksearchservice.search.application.port.LoadPopularKeywordPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadPopularKeywordUseCase {

    private final LoadPopularKeywordPort loadPopularKeywordPort;

    public List<String> execute() {
        log.info("Executing load of top 10 popular keywords");
        return loadPopularKeywordPort.findTop10Keywords();
    }
}
