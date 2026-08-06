package pl.course.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    protected Book() {
    }

    Book(String title) {
        this.title = title;
    }

    Long getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    void setAuthor(Author author) {
        this.author = author;
    }
}

