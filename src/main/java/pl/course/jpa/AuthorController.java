package pl.course.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/1")
    List<AuthorDto> authorsWithNPlusOne() {
        return authorService.findAuthorsWithNPlusOne();
    }

    @GetMapping("/2")
    List<AuthorDto> authorsWithBooks() {
        return authorService.findAuthorsWithBooks();
    }

    @GetMapping("/3")
    Page<AuthorDto> authorsWithNPlusOne(@PageableDefault(size=1) Pageable pageable) {
        return authorService.findAuthorsWithNPlusOnePageable(pageable);
    }
}
