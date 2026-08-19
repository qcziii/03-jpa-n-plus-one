package pl.course.jpa;

public class AuthorProjection {

    private Long id;
    private String name;
    private Long booksCount;

    private AuthorProjection() {
    }

    public AuthorProjection(Long id, String name, Long booksCount) {
        this.id = id;
        this.name = name;
        this.booksCount = booksCount;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Long getBooksCount() {
        return booksCount;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setBooksCount(Long booksCount) {
        this.booksCount = booksCount;
    }
}
