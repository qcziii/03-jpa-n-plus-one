package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = """
    select
            a.id,
            a.name,
            b.title
        from
            author a
        left join
                books b on b.author_id = a.id
        offset
            ? rows
        fetch
            first ? rows only
""", nativeQuery = true)
//@Query(value = "select * from author", nativeQuery = true)
    Page<Author> findAuthorsWithBooks(Pageable pageable);
}

/*
id_author  author_name   book_title
1          1             a
1          1             b

 */