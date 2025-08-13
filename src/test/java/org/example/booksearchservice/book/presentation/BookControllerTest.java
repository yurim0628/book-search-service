package org.example.booksearchservice.book.presentation;

import org.example.booksearchservice.book.domain.BasicInfo;
import org.example.booksearchservice.book.domain.Book;
import org.example.booksearchservice.book.domain.PublicationInfo;
import org.example.booksearchservice.book.infrastructure.persistence.BookEntity;
import org.example.booksearchservice.book.infrastructure.persistence.BookJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookJpaRepository bookJpaRepository;

    private BookEntity savedBookEntity;

    @BeforeEach
    void setup() {
        BookEntity bookEntity = BookEntity.fromDomain(createBook());
        savedBookEntity = bookJpaRepository.save(bookEntity);
    }

    @Test
    @DisplayName("[SUCCESS] 책 상세 조회 API 호출 시 DB와 연동하여 응답 반환")
    void getBookDetail_Success() throws Exception {
        mockMvc.perform(get("/api/books/" + savedBookEntity.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("978-3-16-148410-0"))
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.subtitle").value("Programming Language Guide"))
                .andExpect(jsonPath("$.author").value("Joshua Bloch"))
                .andExpect(jsonPath("$.publisher").value("Addison-Wesley"))
                .andExpect(jsonPath("$.published").value("2018-01-11"));
    }

    private Book createBook() {
        BasicInfo basicInfo = BasicInfo.builder()
                .title("Effective Java")
                .subtitle("Programming Language Guide")
                .author("Joshua Bloch")
                .build();

        PublicationInfo pubInfo = PublicationInfo.builder()
                .publisher("Addison-Wesley")
                .publishedDate(LocalDate.of(2018, 1, 11))
                .build();

        return Book.builder()
                .isbn("978-3-16-148410-0")
                .basicInfo(basicInfo)
                .publicationInfo(pubInfo)
                .build();
    }
}
