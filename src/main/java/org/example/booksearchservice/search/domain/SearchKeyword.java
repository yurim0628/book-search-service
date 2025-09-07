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

    private SearchKeyword(String keyword) {
        if (keyword == null) {
            throw new InvalidSearchQueryException(KEYWORD_REQUIRED);
        }

        String normalizedKeyword = normalize(keyword);
        validate(normalizedKeyword);
        this.value = normalizedKeyword;
    }

    public static SearchKeyword of(String value) {
        return new SearchKeyword(value);
    }

    private static boolean isValidLength(String keyword) {
        int length = keyword.length();
        return length >= MIN_LENGTH && length <= MAX_LENGTH;
    }

    private static boolean matchesPattern(String keyword) {
        return VALID_PATTERN.matcher(keyword).matches();
    }

    private static String normalize(String keyword) {
        return keyword.trim()
                .replaceAll(WHITESPACE_REGEX, SINGLE_SPACE)
                .toLowerCase();
    }

    private void validate(String keyword) {
        if (!isValidLength(keyword)) {
            throw new InvalidSearchQueryException(INVALID_KEYWORD_LENGTH);
        }
        if (!matchesPattern(keyword)) {
            throw new InvalidSearchQueryException(INVALID_KEYWORD_PATTERN);
        }
    }
}
