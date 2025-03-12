package mk.ukim.finki.emt.lab.config;

import jakarta.annotation.PostConstruct;

import mk.ukim.finki.emt.lab.service.AuthorService;
import mk.ukim.finki.emt.lab.service.BookService;
import mk.ukim.finki.emt.lab.model.enums.Category;
import mk.ukim.finki.emt.lab.service.CountryService;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final AuthorService authorService;
    private final BookService bookService;
    private final CountryService countryService;

    public DataInitializer(AuthorService authorService, BookService bookService, CountryService countryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.countryService = countryService;
    }


    private Category randomize(int i) {
        if (i % 7 == 0) return Category.NOVEL;
        else if (i % 7 == 1)return Category.THRILER;
        else if (i % 7 == 2)return Category.HISTORY;
        else if (i % 7 == 3)return Category.FANTASY;
        else if (i % 7 == 4)return Category.BIOGRAPHY;
        else if (i % 7 == 5)return Category.CLASSICS;
        return Category.DRAMA;
    }

    @PostConstruct
    public void initData() {

        for (int i = 1; i < 7; i++) {
            this.countryService.create("Country: " + i, "Continent: " + (i/2+1));
        }

        for (int i = 1; i < 6; i++) {
            this.authorService.create("AuthorName: " + i, "AuthorSurname: " + i, this.countryService.listAll().get((i - 1) % 6).getId());
        }

        for (int i = 1; i < 11; i++) {
            this.bookService.create("Book: " + i, this.randomize(i), this.authorService.listAll().get((i - 1) % 5).getId(), Long.parseLong("7"));
        }
    }
}

