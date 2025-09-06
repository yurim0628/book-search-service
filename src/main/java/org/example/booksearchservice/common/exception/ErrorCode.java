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
    BOOK_NOT_FOUND("책을 찾을 수 없습니다."),

    // Search
    UNSUPPORTED_OPERATOR("지원하지 않는 검색 연산자입니다."),
    INVALID_KEYWORD_COUNT("두 개의 키워드가 필요합니다."),
    KEYWORD_REQUIRED("검색어는 반드시 입력해야합니다."),
    INVALID_KEYWORD_LENGTH("검색 키워드는 2자 이상 50자 이하여야 합니다."),
    INVALID_KEYWORD_PATTERN("허용되지 않은 문자가 포함되어 있습니다.");

    private final String message;
}
