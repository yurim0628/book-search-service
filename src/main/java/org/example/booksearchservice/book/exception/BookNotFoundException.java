package org.example.booksearchservice.book.exception;

import lombok.Getter;
import org.example.booksearchservice.common.exception.NotFoundException;

import static org.example.booksearchservice.common.exception.ErrorCode.BOOK_NOT_FOUND;

@Getter
public class BookNotFoundException extends NotFoundException {

    public BookNotFoundException() {
        super(BOOK_NOT_FOUND);
    }
}
