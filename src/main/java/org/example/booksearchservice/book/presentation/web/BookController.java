package org.example.booksearchservice.book.presentation.web;

import lombok.RequiredArgsConstructor;
import org.example.booksearchservice.book.application.dto.BookDetailResponse;
import org.example.booksearchservice.book.application.service.BookQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookQueryService bookQueryService;

    @GetMapping("/{id}")
    public ResponseEntity<BookDetailResponse> getBookDetail(@PathVariable Long id) {
        BookDetailResponse response = bookQueryService.getBookDetail(id);
        return ResponseEntity.ok(response);
    }
}
