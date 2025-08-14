package org.example.booksearchservice.search.application.dto;

import java.util.List;

public record PopularKeywordResponse(
        List<String> keywords
) {
    public static PopularKeywordResponse of(List<String> keywords) {
        return new PopularKeywordResponse(keywords);
    }
}
