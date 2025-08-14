package org.example.booksearchservice.common.exception;

public class InvalidException extends BusinessException {

    public InvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
