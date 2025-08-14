package org.example.booksearchservice.search.exception;

import org.example.booksearchservice.common.exception.ErrorCode;
import org.example.booksearchservice.common.exception.InvalidException;

public class InvalidSearchQueryException extends InvalidException {

    public InvalidSearchQueryException(ErrorCode errorCode) {
        super(errorCode);
    }
}
