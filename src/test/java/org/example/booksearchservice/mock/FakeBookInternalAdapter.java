package org.example.booksearchservice.mock;

import org.example.booksearchservice.book.application.dto.BookPageResponse;
import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.search.application.port.BookInternalPort;
import org.example.booksearchservice.search.domain.SearchKeyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.example.booksearchservice.fixture.BookFixture.createBooks;

public class FakeBookInternalAdapter implements BookInternalPort {

    @Override
    public BookPageResponse findBooksByKeyword(SearchKeyword keyword, Pageable pageable) {
        List<Book> filteredBooks = filterBooksByKeywords(List.of(keyword.getValue()));
        Page<Book> bookPage = new PageImpl<>(filteredBooks, pageable, filteredBooks.size());
        return BookPageResponse.of(bookPage);
    }

    @Override
    public BookPageResponse findBooksByAnyKeyword(SearchKeyword firstKeyword, SearchKeyword secondKeyword, Pageable pageable) {
        List<Book> filteredBooks = filterBooksByKeywords(List.of(firstKeyword.getValue(), secondKeyword.getValue()));
        Page<Book> bookPage = new PageImpl<>(filteredBooks, pageable, filteredBooks.size());
        return BookPageResponse.of(bookPage);
    }

    @Override
    public BookPageResponse findBooksByKeywordExcluding(SearchKeyword firstKeyword, SearchKeyword secondKeyword, Pageable pageable) {
        List<Book> allBooks = createBooks(5);
        List<Book> filteredBooks = allBooks.stream()
                .filter(book -> containsKeyword(book, firstKeyword.getValue()))
                .filter(book -> !containsKeyword(book, secondKeyword.getValue()))
                .toList();

        Page<Book> bookPage = new PageImpl<>(filteredBooks, pageable, filteredBooks.size());
        return BookPageResponse.of(bookPage);
    }

    private List<Book> filterBooksByKeywords(List<String> keywords) {
        List<Book> allBooks = createBooks(5);
        return allBooks.stream()
                .filter(book -> keywords.stream().anyMatch(keyword -> containsKeyword(book, keyword)))
                .toList();
    }

    private boolean containsKeyword(Book book, String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return book.getBasicInfo().getTitle().toLowerCase().contains(lowerKeyword) ||
                book.getBasicInfo().getSubtitle().toLowerCase().contains(lowerKeyword);
    }
}
