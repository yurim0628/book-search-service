package org.example.booksearchservice.book.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BasicInfo {

    private final String title;
    private final String subtitle;
    private final String author;

    @Builder
    private BasicInfo(String title, String subtitle, String author) {
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
    }
}
