package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a.id FROM Author a")
    Page<Long> findAllAuthorsIds(Pageable pageable);

//
    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books where a.id IN :ids")
    List<Author> findAllAuthorsWithBooks(@Param("ids") List<Long> ids);

}
