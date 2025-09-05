package org.example.booksearchservice.search.domain;

import lombok.Value;
import org.example.booksearchservice.search.exception.InvalidSearchQueryException;

import java.util.regex.Pattern;

import static org.example.booksearchservice.common.exception.ErrorCode.*;

@Value
public class SearchKeyword {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 50;

    private static final Pattern VALID_PATTERN = Pattern.compile("^[a-zA-Z0-9가-힣\\s]+$");

    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String SINGLE_SPACE = " ";

    String value;

    private SearchKeyword(String value) {
        this.value = normalize(value);
    }

    /**
     * 주어진 문자열로부터 SearchKeyword 객체를 생성한다.
     * 유효성 검사를 수행하며, 조건에 맞지 않으면 예외를 던진다.
     */
    public static SearchKeyword of(String value) {
        if (value == null) throw new InvalidSearchQueryException(KEYWORD_REQUIRED);

        String trimmed = value.trim();

        if (!isValidLength(trimmed)) throw new InvalidSearchQueryException(INVALID_KEYWORD_LENGTH);
        if (!matchesPattern(trimmed)) throw new InvalidSearchQueryException(INVALID_KEYWORD_PATTERN);

        return new SearchKeyword(trimmed);
    }

    /**
     * 키워드의 길이가 허용된 범위 내에 있는지 확인한다.
     * 최소 길이: 2자, 최대 길이: 50자
     */
    private static boolean isValidLength(String value) {
        int length = value.length();
        return length >= MIN_LENGTH && length <= MAX_LENGTH;
    }

    /**
     * 키워드가 허용된 문자만 포함하는지 확인한다.
     * 허용 문자: 영문자(a-z, A-Z), 숫자(0-9), 한글, 공백
     * 금지 문자: 특수문자, 구두점, 기호
     */
    private static boolean matchesPattern(String value) {
        return VALID_PATTERN.matcher(value).matches();
    }

    /**
     * 키워드를 정규화한다.
     * 1. 소문자로 변환
     * 2. 연속된 공백을 하나의 공백으로 치환
     * 3. 앞뒤 공백 제거
     */
    private static String normalize(String value) {
        return value.toLowerCase()
                .replaceAll(WHITESPACE_REGEX, SINGLE_SPACE)
                .trim();
    }
}
