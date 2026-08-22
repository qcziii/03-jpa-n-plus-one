package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface AuthorRepository extends JpaRepository<Author, Long> {

//    @EntityGraph(attributePaths = {"books"})
    @Query("select distinct a from Author a left join fetch a.books")
    List<Author> findAllWithBooks();

    @Query("select a.id from Author a")
    Page<Long> findIds(Pageable pageable);

    @EntityGraph(attributePaths = {"books"})
    @Query("select a from Author a where a.id in :ids") //można też jawnie bez EntityGraph: from Author a left join fetch a.books where...
    List<Author> findAllWithBooksThird(@Param("ids") List<Long> ids);
}
