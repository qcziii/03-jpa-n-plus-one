package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(
            value = "SELECT a.id FROM Author a ORDER BY a.id",
            countQuery = "SELECT COUNT(a) FROM Author a"
    )
    Page<Long> findAuthorIds(Pageable pageable);

    @Query("""
            SELECT DISTINCT a
            FROM Author a
            LEFT JOIN FETCH a.books
            WHERE a.id IN :ids
            """)
    List<Author> findAuthorsWithBooksByIds(@Param("ids") Collection<Long> ids);
}