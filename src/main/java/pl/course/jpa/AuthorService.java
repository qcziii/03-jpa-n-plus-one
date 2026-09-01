package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
class AuthorService {

    private final AuthorRepository authorRepository;

    AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    Page<AuthorDto> findAuthorsWithNPlusOne(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .map(this::toDto);
    }

    @Transactional(readOnly = true)
    Page<AuthorDto> findAuthorsPageByIds(Pageable pageable) {
        Page<Long> ids = authorRepository.findAuthorIds(pageable);
        Map<Long, Author> byId = authorRepository.fetchAuthorsWithBooksByIds(ids.getContent()).stream()
                .collect(Collectors.toMap(Author::getId, Function.identity()));
        return ids.map(byId::get).map(this::toDto);
    }

    private AuthorDto toDto(Author author) {
        List<String> bookTitles = author.getBooks().stream()
                .filter(Objects::nonNull)
                .map(Book::getTitle)
                .sorted(Comparator.naturalOrder())
                .toList();
        return new AuthorDto(author.getId(), author.getName(), bookTitles);
    }

    List<AuthorDto> findAll() {
        return authorRepository.findAllBy().stream().map(this::toDto).toList();
    }

    List<AuthorDto> fetchAllAuthorsWithBooks() {
        return authorRepository.fetchAuthorsWithBooks().stream().map(this::toDto).toList();
    }
}
