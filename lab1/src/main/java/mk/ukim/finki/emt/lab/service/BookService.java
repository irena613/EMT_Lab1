package mk.ukim.finki.emt.lab.service;


import mk.ukim.finki.emt.lab.model.Book;
import mk.ukim.finki.emt.lab.model.enums.Category;
import mk.ukim.finki.emt.lab.model.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> listAll();

    Optional<Book> findById(Long id);

    Book create(String name, Category category, Long id, Long availableCopies);
    Optional<Book> create(BookDto book);

//    Book edit(Long id, BookDto book);
    Optional<Book> edit(Long id, BookDto book);

    Book edit(Long id, String name, Category category, Long authorId, Long availableCopies);

    Book delete(Long id);

    Optional<Book> isRented(Long id);


}
