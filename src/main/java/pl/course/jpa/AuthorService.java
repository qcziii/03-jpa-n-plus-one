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

    @Transactional(readOnly = true)
    List<AuthorDto> findAuthorsWithNPlusOne() {
        return authorRepository.findAll()
                .stream()
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

    @Transactional(readOnly = true)
    List<AuthorDto> findAuthorsWithBooks() {
        return authorRepository.findAllWithBooks()
                .stream()
                .map(this::toDto)
                .toList();
    }

    /*
    Page<Author> aPages= authorRepository.findAuthorsWithBooks(pageable);
        return aPages.map(author -> new AuthorDto(
                author.getId(),
                author.getName(),
                author.getBooks().stream().map(Book::getTitle).toList()
        ));



     */
}
