package org.example.booksearchservice.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    COMMON_SYSTEM_ERROR("서버 내부 오류입니다."),
    COMMON_JSON_PROCESSING_ERROR("JSON 처리 오류입니다."),
    COMMON_RESOURCE_NOT_FOUND("리소스를 찾을 수 없습니다."),
    METHOD_ARGUMENT_NOT_VALID("유효하지 않은 요청 값입니다."),

    // Book
    BOOK_NOT_FOUND("책을 찾을 수 없습니다.");

    private final String message;
}
