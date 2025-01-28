package com.bookden.repository;

import com.bookden.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select * from Book where " +
            "lower(title) like lower(concat('%',:searchCriteria,'%'))" +
            "or lower(author) like lower(concat('%',:searchCriteria,'%'))" +
            "or lower(category) like lower(concat('%',:searchCriteria,'%'))",nativeQuery = true)
    Page<Book> findByTitleOrAuthorOrCategory(String searchCriteria, Pageable pageable);

    Optional<Book> findByTitleAndAuthorAndCategory(String title, String author, String category);
}
