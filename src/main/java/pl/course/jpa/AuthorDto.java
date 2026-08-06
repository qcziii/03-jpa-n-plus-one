package pl.course.jpa;

import java.util.List;

record AuthorDto(Long id, String name, List<String> books) {
}

