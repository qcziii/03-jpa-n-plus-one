package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

interface AuthorRepository extends JpaRepository<Author, Long> {
// pobiera wszystkich autorów w jednym zapytaniu, brak ograniczenia jak przy paginacji do ilosci Batcha
    @EntityGraph(attributePaths = "books")
    List<Author> findAllBy();

    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books")
    List<Author> fetchAuthorsWithBooks();

    @Query("select a.id from Author a")
    Page<Long> findAuthorIds(Pageable pageable);

    @Query("select a from Author a left join fetch a.books where a.id in :ids")
    List<Author> fetchAuthorsWithBooksByIds(@Param("ids") Collection<Long> ids);
}
