package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
class AuthorService {

    private final AuthorRepository authorRepository;

    AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

//    @Transactional(readOnly = true)
//    List<AuthorDto> findAuthorsWithNPlusOne() {
//        return authorRepository.findAll().stream()
//                .map(this::toDto)
//                .toList();
//    }

    @Transactional(readOnly = true)
    Page<AuthorDto> findAuthorsWithBooks(Pageable pageable) {
        Page<Author> authorsPage = authorRepository.findAll(pageable);

        List<Long> authorIds = authorsPage.stream()
                .map(Author::getId)
                .toList();

        if (!authorIds.isEmpty()) {
            authorRepository.findAllWithBooksByIdIn(authorIds);
        }

        return authorsPage.map(this::toDto);
    }

    private AuthorDto toDto(Author author) {
        List<String> bookTitles = author.getBooks().stream()
                .map(Book::getTitle)
                .sorted(Comparator.naturalOrder())
                .toList();
        return new AuthorDto(author.getId(), author.getName(), bookTitles);
    }
}
