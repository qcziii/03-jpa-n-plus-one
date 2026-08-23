package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/authors")
class AuthorController {

    private final AuthorService authorService;

    AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    Page<AuthorDto> authorsWithNPlusOne(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Instant start = Instant.now();
        Page<AuthorDto> result = authorService.findAuthorsWithNPlusOne(pageable);
        Instant end = Instant.now();
        System.out.println("Query took: " + Duration.between(start, end).toMillis() + " ms");
        return result;
//        return authorService.findAuthorsWithBooks(pageable);
    }
}
