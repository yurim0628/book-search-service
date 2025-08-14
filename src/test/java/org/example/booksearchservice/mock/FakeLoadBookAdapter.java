package org.example.booksearchservice.mock;

import org.example.booksearchservice.book.application.port.LoadBookPort;
import org.example.booksearchservice.book.domain.BasicInfo;
import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.book.domain.PublicationInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Math.min;
import static java.lang.Math.toIntExact;

public class FakeLoadBookAdapter implements LoadBookPort {

    private final Map<Long, Book> store = new HashMap<>();

    public FakeLoadBookAdapter() {
        store.put(1L, Book.builder()
                .isbn("978-3-16-148410-0")
                .basicInfo(BasicInfo.builder()
                        .title("Effective Java")
                        .subtitle("Programming Language Guide")
                        .author("Joshua Bloch")
                        .build())
                .publicationInfo(PublicationInfo.builder()
                        .publisher("Addison-Wesley")
                        .publishedDate(LocalDate.of(2018, 1, 11))
                        .build())
                .build());

        store.put(2L, Book.builder()
                .isbn("978-0-13-468609-7")
                .basicInfo(BasicInfo.builder()
                        .title("Clean Code")
                        .subtitle("A Handbook of Agile Software Craftsmanship")
                        .author("Robert C. Martin")
                        .build())
                .publicationInfo(PublicationInfo.builder()
                        .publisher("Prentice Hall")
                        .publishedDate(LocalDate.of(2008, 8, 1))
                        .build())
                .build());
    }

    @Override
    public Optional<Book> loadById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Page<Book> loadByKeyword(String keyword, Pageable pageable) {
        List<Book> filteredBooks = store.values().stream()
                .filter(book -> containsIgnoreCase(book.getBasicInfo().getTitle(), keyword))
                .toList();

        return paginate(filteredBooks, pageable);
    }

    @Override
    public Page<Book> loadByAnyKeywords(String firstKeyword, String secondKeyword, Pageable pageable) {
        List<Book> filteredBooks = store.values().stream()
                .filter(book -> {
                    return containsIgnoreCase(book.getBasicInfo().getTitle(), firstKeyword) ||
                            containsIgnoreCase(book.getBasicInfo().getSubtitle(), firstKeyword) ||
                            containsIgnoreCase(book.getBasicInfo().getTitle(), secondKeyword) ||
                            containsIgnoreCase(book.getBasicInfo().getSubtitle(), secondKeyword);
                })
                .toList();

        return paginate(filteredBooks, pageable);
    }

    @Override
    public Page<Book> loadByKeywordExcluding(String firstKeyword, String secondKeyword, Pageable pageable) {
        List<Book> filteredBooks = store.values().stream()
                .filter(book -> {
                    boolean containsFirst = containsIgnoreCase(book.getBasicInfo().getTitle(), firstKeyword) ||
                            containsIgnoreCase(book.getBasicInfo().getSubtitle(), firstKeyword);
                    boolean containsSecond = containsIgnoreCase(book.getBasicInfo().getTitle(), secondKeyword) ||
                            containsIgnoreCase(book.getBasicInfo().getSubtitle(), secondKeyword);
                    return containsFirst && !containsSecond;
                })
                .toList();

        return paginate(filteredBooks, pageable);
    }

    private Page<Book> paginate(List<Book> books, Pageable pageable) {
        int pageOffset = toIntExact(pageable.getOffset());
        int pageLimit = min(pageOffset + pageable.getPageSize(), books.size());
        List<Book> pagedBooks = (pageOffset <= pageLimit) ? books.subList(pageOffset, pageLimit) : List.of();
        return new PageImpl<>(pagedBooks, pageable, books.size());
    }

    private boolean containsIgnoreCase(String source, String target) {
        if (source == null || target == null) return false;
        return source.toLowerCase().contains(target.toLowerCase());
    }
}
