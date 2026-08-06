package pl.course.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
class Author {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Book> books = new LinkedHashSet<>();

    protected Author() {
    }

    Author(String name) {
        this.name = name;
    }

    void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    Long getId() {
        return id;
    }

    String getName() {
        return name;
    }

    Set<Book> getBooks() {
        return books;
    }
}

