package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
class AuthorController {

    private final AuthorService authorService;

    AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    Page<AuthorDto> authorsWithNPlusOne(@PageableDefault(size=10) Pageable pageable) {
        return authorService.findAuthorsWithNPlusOne(pageable);
    }
    @GetMapping("/all")
    ResponseEntity<List<AuthorDto>> findAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @GetMapping("/fetch")
    ResponseEntity<List<AuthorDto>> fetchAllAuthorsWithBooks() {
        return ResponseEntity.ok(authorService.fetchAllAuthorsWithBooks());
    }
}
