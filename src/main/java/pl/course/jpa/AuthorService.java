package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
class AuthorService {

    private final AuthorRepository authorRepository;
    private final PageableArgumentResolver pageableArgumentResolver;

    AuthorService(AuthorRepository authorRepository, PageableArgumentResolver pageableArgumentResolver) {
        this.authorRepository = authorRepository;
        this.pageableArgumentResolver = pageableArgumentResolver;
    }

    @Transactional(readOnly = true)
    Page<AuthorDto> findAuthorsWithNPlusOne(Pageable pageable) {
        Page<Long> authorIds = authorRepository.findAllAuthorsIds(pageable);

        if (authorIds.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<AuthorDto> authorDtos = authorRepository.findAllAuthorsWithBooks(authorIds.getContent())
                .stream()
                .map(this::toDto)
                .toList();

        return new PageImpl<>(authorDtos, pageable, authorIds.getTotalElements());
    }

    private AuthorDto toDto(Author author) {
        List<String> bookTitles = author.getBooks().stream()
                .map(Book::getTitle)
                .sorted(Comparator.naturalOrder())
                .toList();
        return new AuthorDto(author.getId(), author.getName(), bookTitles);
    }
}
