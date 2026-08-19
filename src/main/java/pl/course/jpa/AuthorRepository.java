package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("""
            SELECT DISTINCT a
            FROM Author a
            LEFT JOIN FETCH a.books
            """)
    List<Author> findAllAuthorsWithBookFetching();


    @Query("""
            SELECT new pl.course.jpa.AuthorProjection(
            a.id,
            a.name,
            COUNT(b.id) as booksCount
            )
            FROM Author a
            LEFT JOIN a.books b
            GROUP BY a.id
            """)
    Page<AuthorProjection> findAllAuthorsUsingProjection(Pageable pageable);





}
