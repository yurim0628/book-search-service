package org.example.booksearchservice.search.presentation.web;

import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.search.application.dto.PopularKeywordResponse;
import org.example.booksearchservice.search.application.service.PopularKeywordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/popular-keywords")
@RequiredArgsConstructor
public class PopularKeywordController {

    private final PopularKeywordService popularKeywordService;

    @GetMapping
    public ResponseEntity<PopularKeywordResponse> getPopularKeywords() {
        PopularKeywordResponse response = popularKeywordService.getPopularKeywords();
        return ResponseEntity.ok(response);
    }
}
