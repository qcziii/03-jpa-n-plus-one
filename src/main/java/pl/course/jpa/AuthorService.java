package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private AuthorDto toDto(Author author) {
        List<String> bookTitles = author.getBooks().stream()
                .map(Book::getTitle)
                .sorted(Comparator.naturalOrder())
                .toList();
        return new AuthorDto(author.getId(), author.getName(), bookTitles);
    }

    @Transactional(readOnly = true)
    List<AuthorDto> findAuthorsWithNPlusOne() {
        return authorRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    List<AuthorDto> findAuthorsWithBooks() {
        return authorRepository.findAllWithBooks()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    Page<AuthorDto> findAuthorsWithNPlusOnePageable(Pageable pageable) {
        //można bez tego, ale to zabezpieczenie na sort
        Pageable safe = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                pageable.getSort().and(Sort.by("id")));

        //1. zbieramy id'iki
        Page<Long> idsPage = authorRepository.findIds(safe);
        if (idsPage.isEmpty()) {
            return Page.empty(safe);
        }
        List<Long> ids = idsPage.getContent();

        //2. zbieramy encje
        List<Author> authors = authorRepository.findAllWithBooksThird(ids);

        //3. odtworzenie kolejności (iterowane po ids z pierwszego zapytania)
        Map<Long, Author> byId = authors.stream()
                .collect(Collectors.toMap(Author::getId, Function.identity()));

        List<AuthorDto> content = ids.stream()
                .map(byId::get)
                .filter(Objects::nonNull) //nie obowiązkowe, ale można dodać czy dużym obciążeniu
                .map(this::toDto)
                .toList();

        //4. paginacja z pierwszego zapytania
        return new PageImpl<>(content, safe, idsPage.getTotalElements());

    }
}
