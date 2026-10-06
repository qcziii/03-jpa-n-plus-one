package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
class AuthorService {

    private final AuthorRepository authorRepository;

    AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    Page<AuthorDto> findAuthors(Pageable pageable) {

        Page<Long> authorIdsPage = authorRepository.findAuthorIds(pageable);

        if (authorIdsPage.isEmpty()) {
            return new PageImpl<>(
                    List.of(),
                    pageable,
                    authorIdsPage.getTotalElements()
            );
        }

        List<Long> authorIds = authorIdsPage.getContent();

        Map<Long, Author> authorsById =
                authorRepository.findAuthorsWithBooksByIds(authorIds)
                        .stream()
                        .collect(Collectors.toMap(
                                Author::getId,
                                Function.identity()
                        ));

        List<AuthorDto> content = authorIds.stream()
                .map(authorsById::get)
                .filter(java.util.Objects::nonNull)
                .map(this::toDto)
                .toList();

        return new PageImpl<>(
                content,
                pageable,
                authorIdsPage.getTotalElements()
        );
    }

    private AuthorDto toDto(Author author) {

        List<String> bookTitles = author.getBooks()
                .stream()
                .map(Book::getTitle)
                .sorted(Comparator.naturalOrder())
                .toList();

        return new AuthorDto(
                author.getId(),
                author.getName(),
                bookTitles
        );
    }
}