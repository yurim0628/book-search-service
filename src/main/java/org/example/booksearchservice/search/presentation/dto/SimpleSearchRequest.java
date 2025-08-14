package org.example.booksearchservice.search.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record SimpleSearchRequest(
        @NotBlank(message = "검색어 입력은 필수입니다.")
        String keyword
) {
}
