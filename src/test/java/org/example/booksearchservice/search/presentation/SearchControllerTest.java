package org.example.booksearchservice.search.presentation;

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

import static org.example.booksearchservice.fixture.BookFixture.createBook;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookJpaRepository bookJpaRepository;

    private BookEntity savedBookEntity;

    @BeforeEach
    void setup() {
        bookJpaRepository.deleteAll();
        BookEntity bookEntity = BookEntity.fromDomain(createBook());
        savedBookEntity = bookJpaRepository.save(bookEntity);
    }

    @Test
    @DisplayName("[SUCCESS] 키워드로 책을 검색하면, 200 OK 및 검색 결과를 반환한다")
    void searchBooks_success() throws Exception {
        // given
        String keyword = savedBookEntity.getTitle();

        // when & then
        mockMvc.perform(get("/api/search/books/simple")
                        .param("keyword", keyword)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchQuery").value(keyword))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(1))
                .andExpect(jsonPath("$.books[0].isbn").value(savedBookEntity.getIsbn()))
                .andExpect(jsonPath("$.searchMetaData.strategy").value("NONE"))
                .andExpect(jsonPath("$.searchMetaData.executionTime").isNumber());
    }

    @Test
    @DisplayName("[FAILURE] 키워드가 누락된 경우, 400 Bad Request를 반환한다")
    void searchBooks_keywordMissing_badRequest() throws Exception {
        // when & then
        mockMvc.perform(get("/api/search/books/simple")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[SUCCESS] 복합 쿼리로 책을 검색하면, 200 OK 및 검색 결과를 반환한다")
    void searchBooksByComplexQuery_success() throws Exception {
        // given
        String firstKeyword = savedBookEntity.getTitle().split(" ")[0];
        String secondKeyword = savedBookEntity.getTitle().split(" ")[1];

        // when & then
        mockMvc.perform(get("/api/search/books/complex")
                        .param("query", firstKeyword + "|" + secondKeyword)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageInfo.totalElements").value(1))
                .andExpect(jsonPath("$.books[0].isbn").value(savedBookEntity.getIsbn()))
                .andExpect(jsonPath("$.searchMetaData.strategy").value("OR"))
                .andExpect(jsonPath("$.searchMetaData.executionTime").isNumber());
    }

    @Test
    @DisplayName("[FAILURE] 복합 쿼리 파라미터 누락 시 400 Bad Request를 반환한다")
    void searchBooksByComplexQuery_queryMissing_badRequest() throws Exception {
        mockMvc.perform(get("/api/search/books/complex")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest());
    }

}
