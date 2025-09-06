package org.example.booksearchservice.search.infrastructure.cache;

import org.example.booksearchservice.search.application.port.LoadPopularKeywordPort;
import org.example.booksearchservice.search.application.port.UpdatePopularKeywordPort;
import org.example.booksearchservice.search.domain.SearchKeyword;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPopularKeywordAdapter implements LoadPopularKeywordPort, UpdatePopularKeywordPort {

    private static final int TOP_KEYWORD_LIMIT = 10;

    private final Map<String, Integer> keywordCountMap = new ConcurrentHashMap<>();

    @Override
    public List<String> findTop10Keywords() {
        return keywordCountMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(TOP_KEYWORD_LIMIT)
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public void incrementCount(SearchKeyword keyword) {
        keywordCountMap.merge(keyword.getValue(), 1, Integer::sum);
    }
}

