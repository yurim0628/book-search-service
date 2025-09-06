package org.example.booksearchservice.search.application.port;

import org.example.booksearchservice.search.domain.SearchKeyword;

public interface UpdatePopularKeywordPort {
    void incrementCount(SearchKeyword keyword);
}
