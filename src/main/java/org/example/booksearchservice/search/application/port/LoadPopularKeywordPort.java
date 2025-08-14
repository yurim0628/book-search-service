package org.example.booksearchservice.search.application.port;

import java.util.List;

public interface LoadPopularKeywordPort {
    List<String> findTop10Keywords();
}
