package org.example.booksearchservice.search.presentation;

import org.example.booksearchservice.search.domain.SearchKeyword;
import org.example.booksearchservice.search.infrastructure.cache.InMemoryPopularKeywordAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class PopularKeywordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryPopularKeywordAdapter popularKeywordAdapter;

    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 15; i++) {
            String keyword = "keyword" + i;
            for (int j = 0; j < i; j++) {  // i만큼 count 증가
                popularKeywordAdapter.incrementCount(SearchKeyword.of(keyword));
            }
        }
    }

    @Test
    @DisplayName("[SUCCESS] 인기 키워드 목록을 10개까지 반환한다")
    void getPopularKeywords_returnsTop10() throws Exception {
        mockMvc.perform(get("/api/search/popular-keywords")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keywords", hasSize(10)))
                .andExpect(jsonPath("$.keywords[0]", is("keyword15")))
                .andExpect(jsonPath("$.keywords[9]", is("keyword6")));
    }
}
