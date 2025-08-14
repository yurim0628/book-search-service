package org.example.booksearchservice.search.application.port;

public interface UpdatePopularKeywordPort {
    void incrementCount(String keyword);
}
