package org.example.booksearchservice.book.application.dto;

import org.springframework.data.domain.Page;

public record PageInfo(
        int currentPage,
        int pageSize,
        int totalPages,
        long totalElements
) {
    public static <T> PageInfo of(Page<T> page) {
        return new PageInfo(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}
