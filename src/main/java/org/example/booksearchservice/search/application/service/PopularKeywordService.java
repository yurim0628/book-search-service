package org.example.booksearchservice.search.application.service;

import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.search.application.dto.PopularKeywordResponse;
import org.example.booksearchservice.search.application.usecase.LoadPopularKeywordUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularKeywordService {

    private final LoadPopularKeywordUseCase loadPopularKeywordUseCase;

    public PopularKeywordResponse getPopularKeywords() {
        List<String> keywords = loadPopularKeywordUseCase.execute();
        return PopularKeywordResponse.of(keywords);
    }
}
