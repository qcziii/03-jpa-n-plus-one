package pl.course.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface AuthorRepository extends JpaRepository<Author, Long> {

//    @EntityGraph(attributePaths = {"books"})
    @Query("select distinct a from Author a left join fetch a.books")
    List<Author> findAllWithBooks();
}
