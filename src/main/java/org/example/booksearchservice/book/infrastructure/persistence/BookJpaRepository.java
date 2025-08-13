package org.example.booksearchservice.book.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {

    @Query("""
        SELECT b
        FROM BookEntity b
        WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(b.subtitle) LIKE LOWER(CONCAT('%', :keyword, '%'))
        """)
    Page<BookEntity> findByKeyword(String keyword, Pageable pageable);
}
