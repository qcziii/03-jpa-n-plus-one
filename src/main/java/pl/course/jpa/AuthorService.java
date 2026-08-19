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

    Page<AuthorProjection> findAllAuthorsUsingProjection(Pageable pageable) {
       return authorRepository.findAllAuthorsUsingProjection(pageable);
    }

    @Transactional(readOnly = true)
    List<AuthorDto> findAuthorsWithNPlusOne() {
        return authorRepository.findAllAuthorsWithBookFetching().stream()
                .map(this::toDto)
                .toList();
    }

    private AuthorDto toDto(Author author) {
        List<String> bookTitles = author.getBooks().stream()
                .map(Book::getTitle)
                .sorted(Comparator.naturalOrder())
                .toList();
        return new AuthorDto(author.getId(), author.getName(), bookTitles);
    }
}
