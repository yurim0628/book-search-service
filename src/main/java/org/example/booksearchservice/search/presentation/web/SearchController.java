package org.example.booksearchservice.search.presentation.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.search.application.dto.SearchResponse;
import org.example.booksearchservice.search.application.service.SearchService;
import org.example.booksearchservice.search.presentation.dto.ComplexSearchRequest;
import org.example.booksearchservice.search.presentation.dto.SimpleSearchRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search/books")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    private static final String  DEFAULT_PAGE = "0";
    private static final String DEFAULT_SIZE = "20";

    @GetMapping("/simple")
    public ResponseEntity<SearchResponse> searchBooks(
            @Valid @ModelAttribute SimpleSearchRequest request,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_SIZE) int size) {

        Pageable pageable = PageRequest.of(page, size);
        SearchResponse response = searchService.searchBooksByKeyword(request.keyword(), pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping ("/complex")
    public ResponseEntity<SearchResponse> searchBooksByComplexQuery(
            @Valid @ModelAttribute ComplexSearchRequest request,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_SIZE) int size) {

        Pageable pageable = PageRequest.of(page, size);
        SearchResponse response = searchService.searchBooksByComplexQuery(request.query(), pageable);
        return ResponseEntity.ok(response);
    }
}
