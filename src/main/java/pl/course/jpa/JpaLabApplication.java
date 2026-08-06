package pl.course.jpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaLabApplication.class, args);
    }

    @Bean
    CommandLineRunner seedData(AuthorRepository authorRepository) {
        return args -> {
            if (authorRepository.count() > 0) {
                return;
            }

            Author leGuin = new Author("Ursula K. Le Guin");
            leGuin.addBook(new Book("A Wizard of Earthsea"));
            leGuin.addBook(new Book("The Left Hand of Darkness"));

            Author lem = new Author("Stanisław Lem");
            lem.addBook(new Book("Solaris"));
            lem.addBook(new Book("The Cyberiad"));

            Author tolkien = new Author("J.R.R. Tolkien");
            tolkien.addBook(new Book("The Hobbit"));
            tolkien.addBook(new Book("The Lord of the Rings"));

            authorRepository.save(leGuin);
            authorRepository.save(lem);
            authorRepository.save(tolkien);
        };
    }
}

